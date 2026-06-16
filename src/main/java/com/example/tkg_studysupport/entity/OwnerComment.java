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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "owner_comments")
public class OwnerComment {
    
    protected OwnerComment(){}

    public OwnerComment(StudyLog commentedIn, OwnerProfile commentedBy, String comment){
        this.commentedIn = commentedIn;
        this.commentedBy = commentedBy;
        this.comment = comment;
        this.commentedAt = LocalDateTime.now();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    /* たくさんのOwnerCommentが１つのStudyLogに紐づく. */
    /** どのログでコメントしたか。コミュニティはログのエンティティに内包されている。 */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "commented_in", nullable = false, unique = false)
    private StudyLog commentedIn;

    /** どの講師がコメントしたか。 */
    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "commented_by", nullable = false, unique = false)
    private OwnerProfile commentedBy;

    /** いつ記録したかを保管する。 */
    @NotNull
    @Column(name = "commented_at", nullable = false)
    private LocalDateTime commentedAt;

    /** コメントの内容。 */
    @NotBlank
    @Column(name= "comment", nullable = false, length = 1024)
    private String comment;

    public String getComment() {
        return comment;
    }

    public Long getCommentId() {
        return commentId;
    }

    public LocalDateTime getCommentedAt() {
        return commentedAt;
    }

    public OwnerProfile getCommentedBy() {
        return commentedBy;
    }

    public StudyLog getCommentedIn() {
        return commentedIn;
    }

}
