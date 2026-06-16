package com.example.tkg_studysupport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.tkg_studysupport.entity.OwnerComment;
import com.example.tkg_studysupport.entity.StudyLog;

public interface OwnerCommentRepository
        extends JpaRepository<OwnerComment, Long> {

    /* InはSpring Data JPAの予約語であるため, メソッド名に含むことができない. */
    @Query("""
            SELECT oc
            FROM OwnerComment oc
            WHERE oc.commentedIn = :studyLog
            ORDER BY oc.commentedAt ASC
            """)
    List<OwnerComment> findByCommentedInOrderByCommentedAtAsc(
            @Param("studyLog") StudyLog studyLog
    );
}