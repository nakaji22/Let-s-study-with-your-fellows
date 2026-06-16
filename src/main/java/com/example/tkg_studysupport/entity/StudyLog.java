package com.example.tkg_studysupport.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/** 各生徒が記録した勉強時間を保管するエンティティ。 */
@Entity
@Table(name = "study_logs")
public class StudyLog {
    
    protected StudyLog(){}

    public StudyLog(StudentProfile loggedBy, Community community, int studyMinutes, LocalDate studiedOn){
        this.loggedBy = loggedBy;
        this.community = community;
        this.createdAt = LocalDateTime.now();
        this.studiedOn = studiedOn;
        this.studyMinutes = studyMinutes;
    }

    /** 主キー。勉強記録ごとにuniqueなキーをつける。 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studyLogId;

    /* 外部キーとして参照したいStudentProfileのキーはもともと主キーなので、referencedColumnNameは省略可. */
    /** 誰が記録したかを保管する。 */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "logged_by", nullable = false)
    private StudentProfile loggedBy;

    /** どのコミュニティ内で記録したかを保管する。 */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "logged_in", nullable = false)
    private Community community;

    /** いつ勉強したかを記録する。 */
    @NotNull
    @Column(name = "studied_on", nullable = false)
    private LocalDate studiedOn;

    /** いつ記録したかを保管する。 */
    @NotNull
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    /** 何分勉強したかを保管する。 */
    @Positive
    @Max(1440)
    @Column(name = "study_minutes", nullable = false)
    private int studyMinutes;

    /* Getterの定義 */
    public Long getstudyLogId() {
        return studyLogId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public StudentProfile getLoggedBy() {
        return loggedBy;
    }
    
    public Community getcommunity() {
        return community;
    }

    public int getStudyMinutes() {
        return studyMinutes;
    }

    public LocalDate getStudiedOn() {
        return studiedOn;
    }

}