package com.example.tkg_studysupport.controller;

import com.example.tkg_studysupport.service.AccountService;
import com.example.tkg_studysupport.form.StudentSignupForm;
import com.example.tkg_studysupport.exception.DuplicateLoginIdException;
import com.example.tkg_studysupport.exception.PasswordMismatchException;
import com.example.tkg_studysupport.exception.OwnerCodeMismatchException;
import com.example.tkg_studysupport.form.OwnerSignupForm;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import jakarta.validation.Valid;

/** 生徒登録画面の表示とServiceへの登録依頼、講師登録画面の表示とServiceへの登録依頼を担当する。 */
/* @RequestMappingはブラウザからのURL(リクエスト)をJavaの処理に結び付けるアノテーション.  */
@Controller
@RequestMapping("/signup")
public class SignupController {

    /* AccountServiceは@ServiceによりSpringに保存されているので、newなしで宣言可能 */
    /** 生徒と講師の登録を行うメソッドをもつクラスをインスタンス化。 */
    private final AccountService accountService;

    /* コンストラクタが１つの場合、@Autowiredは省略可能 */
    public SignupController(AccountService accountService){
        this.accountService = accountService;
    }

    /** "/student"にアクセスがあった場合、空のStudentSignupFormを作成し、Modelに格納して登録画面のURLを返す */
    @GetMapping("/student")
    public String displayStudentRegister(Model model){
        model.addAttribute("studentSignupForm", new StudentSignupForm());
        return "student-signup";
    }
    
    /* @ModelAttribute:CommunityCreateForm の各フィールドへ値を代入する. */
    /* @Valid:CommunityCreateForm に付いた制約を検証する. */
    /* 検証結果を BindingResult に格納する. */
    /** 例外がなければ生徒の登録を行う。成功すれば"/login"にリダイレクトし、例外が起こればエラーメッセージを受け取って登録画面に戻す。 */
    @PostMapping("/student")
    public String makeStudentSignup(@ModelAttribute @Valid StudentSignupForm studentSignupForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "student-signup";
        }
        try{ 
            accountService.registerStudent(studentSignupForm);
        } catch(DuplicateLoginIdException e) {
            bindingResult.rejectValue(
            "loginId",
            "duplicate",
            e.getMessage()
            );
            return "student-signup";
        } catch(PasswordMismatchException e) {
            bindingResult.rejectValue(
            "passwordConfirmation",
            "mismatches",
            e.getMessage()
            );
            return "student-signup";
        }
        return "redirect:/login";
    }

    /** "/owner"にアクセスがあった場合、空のStudentSignupFormを作成し、Modelに格納して登録画面のURLを返す */
    @GetMapping("/owner")
    public String displayOwnerRegister(Model model){
        model.addAttribute("ownerSignupForm", new OwnerSignupForm());
        return "owner-signup";
    }

    /** 例外がなければ講師の登録を行う。成功すれば"/login"にリダイレクトし、例外が起こればエラーメッセージを受け取って登録画面に戻す。 */
    @PostMapping("/owner")
    public String makeOwnerRegister(@ModelAttribute @Valid OwnerSignupForm ownerSignupForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "owner-signup";
        }
        try{
            accountService.registerOwner(ownerSignupForm);
        } catch(DuplicateLoginIdException e) {
            bindingResult.rejectValue(
                "loginId",
                "duplicated",
                e.getMessage()
            );
            return "owner-signup";
        } catch(PasswordMismatchException e){
            bindingResult.rejectValue(
                "passwordConfirmation",
                "mismatches",
                e.getMessage()
            );
            return "owner-signup";
        }  catch(OwnerCodeMismatchException e){
            bindingResult.rejectValue(
                "ownerRegistrationCode",
                "mismatches",
                e.getMessage()
            );
            return "owner-signup";
        }
        return "redirect:/login";
    }
}
