package com.example.tkg_studysupport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import com.example.tkg_studysupport.entity.Account;
/**
 * <p>
 * 下記の内容を担当する。
 * ・Accountを保存する
 * ・DB内部IDでAccountを探す
 * ・loginIdでAccountを探す
 * ・loginIdがすでに存在するか確認する
 */
/* JpaRepository<操作対象Entity型, 主キー>を継承. 以下は定義済み save,findById,findAll,deleteById,existsById,count */
public interface AccountRepository extends JpaRepository<Account, Long> {
    
    /* interfaceなのでメソッドの宣言のみ行う */
    /* JPAがメソッド名を解析してloginIdが一致するAccountを探す */
    /** loginIdからアカウント情報を取得する。 */
    public Optional<Account> findByLoginId(String loginId);

    /* JPAがメソッド名を解析してloginIdが一致するAccountが存在するか確認する */
    /** loginIdが既に存在しているか確認する。 */
    public boolean existsByLoginId(String loginId);
}
