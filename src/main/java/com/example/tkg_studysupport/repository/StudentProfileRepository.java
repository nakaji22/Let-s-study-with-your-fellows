package com.example.tkg_studysupport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import com.example.tkg_studysupport.entity.Account;
import com.example.tkg_studysupport.entity.StudentProfile;
/**
 * AccountとStudentProfileを紐づける。
 */
/* interface内のメソッドは暗黙的に public abstract になる. */
public interface StudentProfileRepository extends JpaRepository<StudentProfile, Long>{
    /** Accountから対応するStudentProfileを返す。 */
    /* 結果がない場合はnullではなく空のOptionalとなり, NullPointerExceptionを防ぐ. */
    Optional<StudentProfile> findByAccount(Account account);
}
