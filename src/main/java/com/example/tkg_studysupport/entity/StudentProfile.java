package com.example.tkg_studysupport.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.FetchType;
import jakarta.validation.constraints.NotNull;

/**
 * 生徒固有のプロフィール情報を表すEntity。
 * 対応するAccountと学年を保持する。
 */
@Entity
@Table(name = "student_profiles")
public class StudentProfile {
    /**
     * Hibernateが使用する引数なしコンストラクタ。
     */
    protected StudentProfile(){}

    /** アカウント登録用コンストラクタ */
    public StudentProfile(Account account, Grade grade){
        this.account = account;
        this.grade = grade;
    }

    /** DBが自動採番するID。 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* 必要になった時だけaccountを取得. EAGERならStudentProfile を取得した時点で即時取得. */
    /* Account型の主キーであるidを外部キーとする. */
    /** 対応する生徒アカウント。 */
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false, unique = true)
    @NotNull
    private Account account;

    /** 生徒の学年。 */
    @Column(name = "grade", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private Grade grade;

    public Long getId(){
        return this.id;
    }

    public Account getAccount(){
        return this.account;
    }

    public Grade getGrade(){
        return this.grade;
    }

}
