package com.example.tkg_studysupport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tkg_studysupport.entity.Community;
import com.example.tkg_studysupport.entity.StudyLog;

public interface StudyLogRepository extends JpaRepository<StudyLog, Long>{
    /** Communityから勉強記録を時系列順に返す。 */
    List<StudyLog> findByCommunityOrderByStudiedOnAscCreatedAtAsc(
            Community community
    );
}
