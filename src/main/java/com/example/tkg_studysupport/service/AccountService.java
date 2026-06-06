package com.example.tkg_studysupport.service;

import com.example.tkg_studysupport.entity.Account;
import com.example.tkg_studysupport.form.OwnerSignupForm;
import com.example.tkg_studysupport.form.StudentSignupForm;
import com.example.tkg_studysupport.repository.AccountRepository;
import com.example.tkg_studysupport.repository.OwnerProfileRepository;
import com.example.tkg_studysupport.repository.StudentProfileRepository;
import com.example.tkg_studysupport.entity.StudentProfile;
import com.example.tkg_studysupport.entity.OwnerProfile;
import com.example.tkg_studysupport.exception.DuplicateLoginIdException;
import com.example.tkg_studysupport.exception.PasswordMismatchException;
import com.example.tkg_studysupport.exception.OwnerCodeMismatchException;
import com.example.tkg_studysupport.entity.AccountRole;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Objects;

/**
 * 生徒登録、講師登録を行う。
 * loginIdの既存判定やパスワードと確認用パスワードの一致判定、パスワードのハッシュ化などを行う。
 */
/* Service層を宣言するアノテーション. アプリケーション開始時に唯一のコンストラクタ注入を行う(newしなくても他クラスでフィールドとして宣言可能。。 */
/* @Serviceはクラスにつけ、@Beanはメソッドにつける。 */
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final OwnerProfileRepository ownerProfileRepository;
    private final PasswordEncoder passwordEncoder;
    private final String ownerRegistrationCodeHash;

    public AccountService(
            AccountRepository accountRepository,
            StudentProfileRepository studentProfileRepository,
            OwnerProfileRepository ownerProfileRepository,
            PasswordEncoder passwordEncoder,
            @Value("${app.owner-registration-code-hash}") String ownerRegistrationCodeHash
    ) {
        this.accountRepository = accountRepository;
        this.studentProfileRepository = studentProfileRepository;
        this.ownerProfileRepository = ownerProfileRepository;
        this.passwordEncoder = passwordEncoder;
        this.ownerRegistrationCodeHash = ownerRegistrationCodeHash;
    }

    /** 生徒登録用トランザクショナルメソッド。
     * ID重複確認⇒パスワード一致確認⇒アカウント型作成⇒レポジトリのDB保存
     * ⇒生徒プロファイル型作成⇒保存済みアカウントの連携⇒生徒プロファイルレポジトリのDb保存
    */
    @Transactional
    public Account registerStudent(StudentSignupForm form){
        validateLoginIdNotDuplicated(form.getLoginId());
        validatePasswordMatches(form.getPassword(), form.getPasswordConfirmation());
        String passwordHash = passwordEncoder.encode(form.getPassword());

        Account account = new Account(
            form.getLoginId(), 
            passwordHash, 
            form.getDisplayName(),
            AccountRole.STUDENT
        );

        Account savedAccount = accountRepository.save(account);

        StudentProfile studentProfile = new StudentProfile(
            savedAccount,
            form.getGrade()
        );

        studentProfileRepository.save(studentProfile);

        return savedAccount;
    }

    /** 講師登録用トランザクショナルメソッド。
     * ID重複確認⇒パスワード一致確認⇒アカウント型作成⇒レポジトリのDB保存
     * ⇒講師プロファイル型作成⇒保存済みアカウントの連携⇒講師プロファイルレポジトリのDb保存
    */
    @Transactional
    public Account registerOwner(OwnerSignupForm form){
        validateLoginIdNotDuplicated(form.getLoginId());
        validatePasswordMatches(form.getPassword(), form.getPasswordConfirmation());
        validateCodeMatchs(form.getOwnerRegistrationCode(), ownerRegistrationCodeHash);
        String passwordHash = passwordEncoder.encode(form.getPassword());

        Account account = new Account(
            form.getLoginId(),
            passwordHash,
            form.getDisplayName(),
            AccountRole.OWNER
        );

        Account savedAccount = accountRepository.save(account);

        OwnerProfile ownerProfile = new OwnerProfile(
            savedAccount
        );

        ownerProfileRepository.save(ownerProfile);

        return savedAccount;

    }

    /** loginId重複確認用例外処理。 */
    private void validateLoginIdNotDuplicated(String loginId){
        if(accountRepository.existsByLoginId(loginId)){
            throw new DuplicateLoginIdException("このIDは既に使用されています。");
        }
    }

    /** パスワード&確認用パスワード一致確認用例外処理。 */
    private void validatePasswordMatches(String password, String passwordConfirmation){
        if(!Objects.equals(password, passwordConfirmation)){
            throw new PasswordMismatchException("パスワードと確認用パスワードが一致していません。");
        }
    }

    private void validateCodeMatchs(String inputOwnerCode, String ownerRegistrationCode){
        /* matches(平文, ハッシュ) */
        if(!passwordEncoder.matches(inputOwnerCode, ownerRegistrationCode)){
            throw new OwnerCodeMismatchException("入力された講師コードに間違いがあります。");
        }
    }

}
