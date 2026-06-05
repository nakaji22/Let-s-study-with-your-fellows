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
@Controller
@RequestMapping("/signup")
public class SignupController {

    private final AccountService accountService;

    public SignupController(AccountService accountService){
        this.accountService = accountService;
    }

    @GetMapping("/student")
    public String displayStudentRegister(Model model){
        model.addAttribute("studentSignupForm", new StudentSignupForm());
        return "student-signup";
    }

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

    @GetMapping("/owner")
    public String displayOwnerRegister(Model model){
        model.addAttribute("ownerSignupForm", new OwnerSignupForm());
        return "owner-signup";
    }

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
