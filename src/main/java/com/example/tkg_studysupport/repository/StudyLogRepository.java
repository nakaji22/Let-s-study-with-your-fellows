package com.example.tkg_studysupport.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tkg_studysupport.entity.Community;
import com.example.tkg_studysupport.entity.CommunityMembership;
import com.example.tkg_studysupport.entity.StudentProfile;
import com.example.tkg_studysupport.entity.StudyLog;

public interface StudyLogRepository extends JpaRepository<StudyLog, Long>{
    /** Communityから勉強記録を時系列順に返す。 */
    List<StudyLog> findByCommunityOrderByStudiedOnAscCreatedAtAsc(
            Community community
    );
    
    /** 登録した生徒が本当にそのコミュニティに参加しているか確認する. */
    Optional<CommunityMembership> findByCommunityAndStudentAndActiveTrue(
            Community community,
            StudentProfile student
    );

}
