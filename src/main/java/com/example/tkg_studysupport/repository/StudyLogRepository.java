package com.example.tkg_studysupport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.tkg_studysupport.entity.Community;
import com.example.tkg_studysupport.entity.StudyLog;

public interface StudyLogRepository extends JpaRepository<StudyLog, Long> {

    /* 勉強記録一覧を取得するときに、表示に必要な関連データも一緒に読み込むためのJPQL. */
    /* <td th:text="${studyLog.loggedBy.account.displayName}">でデータをたどる */
    // 指定されたコミュニティに登録された StudyLog を取得する。
    // そのとき、各 StudyLog に紐づく StudentProfile と Account も一緒に読み込む。
    // 結果は、勉強日が古い順、同じ日なら登録日時が古い順に並べる。
    @Query("""
            SELECT sl
            FROM StudyLog sl
            JOIN FETCH sl.loggedBy student
            JOIN FETCH student.account
            WHERE sl.community = :community
            ORDER BY sl.studiedOn ASC, sl.createdAt ASC
            """)
    // JPQL内の :community に、
    // メソッド引数の Community community を入れる
    List<StudyLog> findByCommunityOrderByStudiedOnAscCreatedAtAsc(
            @Param("community") Community community
    );
}