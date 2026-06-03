package com.example.tkg_studysupport.service;

import com.example.tkg_studysupport.entity.Account;
import com.example.tkg_studysupport.form.StudentSignupForm;
import com.example.tkg_studysupport.repository.AccountRepository;
import com.example.tkg_studysupport.repository.OwnerProfileRepository;
import com.example.tkg_studysupport.repository.StudentProfileRepository;
import com.example.tkg_studysupport.entity.Grade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.tkg_studysupport.exception.DuplicateLoginIdException;
import com.example.tkg_studysupport.exception.PasswordMismatchException;

/**
 * 生徒登録、講師登録を行う。
 * loginIdの既存判定やパスワードと確認用パスワードの一致判定、パスワードのハッシュ化などを行う。
 */
/* Service層を宣言するアノテーション. アプリケーション開始時に唯一のコンストラクタ注入を行う。 */
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final OwnerProfileRepository ownerProfileRepository;

    public AccountService(
            AccountRepository accountRepository,
            StudentProfileRepository studentProfileRepository,
            OwnerProfileRepository ownerProfileRepository
    ) {
        this.accountRepository = accountRepository;
        this.studentProfileRepository = studentProfileRepository;
        this.ownerProfileRepository = ownerProfileRepository;
    }

    @Transactional
    public Account registerStudent(StudentSignupForm form){
        AccountRepository accountRepository;
        if(validateLoginIdNotDuplicated(form.getLoginId())){

        }
    }

    private void validateLoginIdNotDuplicated(String loginId){
        if(accountRepository.existsByLoginId(loginId)){
            throw new DuplicateLoginIdException("このIDは既に使用されています。");
        }
    }

    private void validatePasswordMatches(String password, String passwordComfirmation){
        if(!password.equals(passwordComfirmation)){
            throw new PasswordMismatchException("パスワードと確認用パスワードが一致していません。");
        }
    }
}
