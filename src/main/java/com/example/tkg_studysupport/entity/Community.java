package com.example.tkg_studysupport.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "communities")
public class Community {

    protected Community(){}

    public Community(String communityName, String joinPasswordHash, OwnerProfile createdBy){
        this.communityName = communityName;
        this.joinPasswordHash = joinPasswordHash;
        this.createdBy = createdBy;
        this.createdAt = LocalDateTime.now();
        this.active = true;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long communityId;

    @NotBlank
    @Size(max = 50)
    @Column(name = "community_name", nullable = false, length=50)
    private String communityName;

    @NotBlank
    @Column(name = "join_password_hash", nullable = false, length = 255)
    @Size(max = 255)
    private String joinPasswordHash;

    /* referencedColumnNameを省略すると、参照先Entityの主キーが使われる. */
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "created_by_owner_id", nullable = false)
    private OwnerProfile createdBy;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "active", nullable = false)
    private boolean active;

    public Long getCommunityId() {
        return communityId;
    }

    public String getCommunityName() {
        return communityName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public OwnerProfile getCreatedBy() {
        return createdBy;
    }

    public String getJoinPasswordHash() {
        return joinPasswordHash;
    }

    public boolean isActive() {
        return active;
    }
}
