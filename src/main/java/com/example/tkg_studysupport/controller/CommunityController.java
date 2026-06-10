package com.example.tkg_studysupport.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.tkg_studysupport.exception.OwnerProfileNotFoundException;
import com.example.tkg_studysupport.form.CommunityCreateForm;
import com.example.tkg_studysupport.service.CommunityService;

import jakarta.validation.Valid;

/** コミュニティの作成を担当するコントローラー。 */
@Controller
@RequestMapping("/communities")
public class CommunityController {
    
    private final CommunityService communityService;

    public CommunityController(CommunityService communityService){
        this.communityService = communityService;
    }

    /** GET通信⇒空のCommunityCreateFormを作成し画面表示。 */
    @GetMapping("/create")
    public String displayCommunityCreate(Model model){
        model.addAttribute("communityCreateForm", new CommunityCreateForm());
        return "community-create";
    }

    /* @ModelAttribute:CommunityCreateForm の各フィールドへ値を代入する. */
    /* @Valid:CommunityCreateForm に付いた制約を検証する. */
    /* 検証結果を BindingResult に格納する. */
    /** POST通信⇒フォームの入力値チェック⇒講師のログインIdを取得⇒サービス層へコミュニティ作成を一任。 */
    @PostMapping("/create")
    public String createCommunity(@ModelAttribute @Valid CommunityCreateForm communityCreateForm,
                                  BindingResult bindingResult,
                                  Authentication authentication){
        /* authenticationは現在ログインしているユーザーの認証情報をControllerで受け取るための引数。 */
        if(bindingResult.hasErrors()){
            return "community-create";
        }
        String loginId = authentication.getName();
        try{
            communityService.createCommunity(loginId, communityCreateForm);
        } catch(OwnerProfileNotFoundException e){
            bindingResult.reject(
            "notfound",
            e.getMessage()
            );
            return "community-create";           
        }

        return "redirect:/home";
    }

}
