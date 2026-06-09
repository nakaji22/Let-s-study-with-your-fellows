package com.example.tkg_studysupport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tkg_studysupport.entity.Community;

/** コミュニティに関するレポジトリ。 */
public interface CommunityRepository extends JpaRepository<Community, Long>{
    
    /** コミュニティ名による部分検索を行う。 */
    public List<Community> findByCommunityNameContainingIgnoreCaseAndActiveTrue(String communityName);

}

// | 部分              | 意味                                        |
// | --------------- | ----------------------------------------- |
// | `findBy`        | 条件に一致するデータを検索する                           |
// | `CommunityName` | `Community` の `communityName` フィールドを対象にする |
// | `Containing`    | 部分一致で検索する                                 |
// | `IgnoreCase`    | 英字の大文字・小文字を区別しない                          |
// | `And`           | 条件を追加する                                   |
// | `ActiveTrue`    | `active` が `true` のデータだけ取得する              |
