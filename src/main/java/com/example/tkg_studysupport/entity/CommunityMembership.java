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

    protected CommunityMembership(){}

    public CommunityMembership(Community community, StudentProfile student){
        this.community = community;
        this.student = student;
        this.joinedAt = LocalDateTime.now();
        this.active = true;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long membershipId;

    /* optional = false は、その関連先Entityが必ず存在しなければならないことを表す. */

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "community_id", nullable = false)
    private Community community;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_profile_id", nullable = false)
    private StudentProfile student;

    @NotNull
    @Column(name = "joined_at", nullable = false)
    private LocalDateTime joinedAt;

    @Column(name="active", nullable = false)
    private boolean active;

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

    public void EnableActive() {
        this.active = true;
    }

    public void UnEnableActive() {
        this.active = false;
    }

}
