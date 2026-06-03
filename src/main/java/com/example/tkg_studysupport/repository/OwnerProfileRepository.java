package com.example.tkg_studysupport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import com.example.tkg_studysupport.entity.Account;
import com.example.tkg_studysupport.entity.OwnerProfile;
/**
 * AccountとOwnerProfileを紐づける。
 */
/* interface内のメソッドは暗黙的に public abstract になる. */
public interface OwnerProfileRepository extends JpaRepository<OwnerProfile, Long>{
    /** Accountから対応するOwnerProfileを返す。 */
    /* 結果がない場合はnullではなく空のOptionalとなり, NullPointerExceptionを防ぐ. */
    Optional<OwnerProfile> findByAccount(Account account);
}
