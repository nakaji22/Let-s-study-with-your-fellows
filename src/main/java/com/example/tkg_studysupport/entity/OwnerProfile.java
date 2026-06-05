package com.example.tkg_studysupport.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.FetchType;
import jakarta.validation.constraints.NotNull;

/**
 * 講師固有のプロフィール情報を表すEntity。
 * 対応するAccountを保持する。
 */
@Entity
@Table(name = "owner_profiles")
public class OwnerProfile {
    /**
     * Hibernateが使用する引数なしコンストラクタ。
     */
    protected OwnerProfile(){}

    /** アカウント登録用コンストラクタ */
    public OwnerProfile(Account account){
        this.account = account;
    }

    /** DBが自動採番するID。 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* 必要になった時だけaccountを取得. EAGERならOwnerProfile を取得した時点で即時取得. */
    /* Account型の主キーであるidを外部キーとする. */
    /** 対応する講師アカウント。 */
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false, unique = true)
    @NotNull
    private Account account;

    public Long getId(){
        return this.id;
    }

    public Account getAccount(){
        return this.account;
    }
}
