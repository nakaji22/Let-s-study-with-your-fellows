package com.example.tkg_studysupport.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tkg_studysupport.entity.Community;
import com.example.tkg_studysupport.entity.CommunityMembership;
import com.example.tkg_studysupport.entity.StudentProfile;

//* コミュニティと参加生徒の対応関係に関するレポジトリ。 */
public interface CommunityMembershipRepository extends JpaRepository<CommunityMembership, Long>{

    /** 生徒とコミュニティの組み合わせで検索する。 */
    Optional<CommunityMembership> findByCommunityAndStudent(
        Community community,
        StudentProfile student
    );

    /** 現在参加中か確認する。 */
    boolean existsByCommunityAndStudentAndActiveTrue(
        Community community,
        StudentProfile student
    );

    /** 特定コミュニティに所属する生徒一覧を取得する。 */
    List<CommunityMembership> findByCommunityAndActiveTrue(
        Community community
    );

    /** 生徒が参加しているコミュニティ一覧を取得する。 */
    List<CommunityMembership> findByStudentAndActiveTrue(
        StudentProfile student
    );

    Optional<CommunityMembership> findByCommunityAndStudentAndActiveTrue(
            Community community,
            StudentProfile student
    );

}
