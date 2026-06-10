package com.example.tkg_studysupport.entity;

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
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;

/* 同じ生徒が同じコミュニティに重複参加できないように、
   community_id と student_profile_id の組み合わせに一意制約を付ける。 */
/** 生徒と参加コミュニティの対応関係を表すエンティティ。 */
@Entity
@Table(
    name = "community_memberships",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_membership_community_student",
            columnNames = {"community_id", "student_profile_id"}
        )
    }
)
public class CommunityMembership {
    /**
     * Hibernateが使用する引数なしコンストラクタ。
     */
    protected CommunityMembership(){}

    /** メンバーシップ登録用コンストラクタ。 */
    public CommunityMembership(Community community, StudentProfile student){
        this.community = community;
        this.student = student;
        this.joinedAt = LocalDateTime.now();
        this.active = true;
    }

    /** メンバーシップID。 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long membershipId;

    /* optional = false は、その関連先Entityが必ず存在しなければならないことを表す. */

    /** 参加コミュニティ。外侮キー。 */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "community_id", nullable = false)
    private Community community;

    /** 参加している生徒。外部キー。 */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_profile_id", nullable = false)
    private StudentProfile student;

    /** 参加日時。 */
    @NotNull
    @Column(name = "joined_at", nullable = false)
    private LocalDateTime joinedAt;

    /** 有効性。 */
    @Column(name="active", nullable = false)
    private boolean active;

    /* Getterの定義。 */
    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    public Community getCommunity() {
        return community;
    }

    public StudentProfile getStudent() {
        return student;
    }

    public Long getMembershipId() {
        return membershipId;
    }

    public boolean isActive() {
        return active;
    }

    /* Activeに関するSetterの定義。 */
    public void reactive() {
        this.active = true;
    }

    public void deactive() {
        this.active = false;
    }

}
