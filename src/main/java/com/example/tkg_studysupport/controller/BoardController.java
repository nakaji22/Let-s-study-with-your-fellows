package com.example.tkg_studysupport.controller;

import java.time.LocalDate;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.tkg_studysupport.entity.Account;
import com.example.tkg_studysupport.entity.Community;
import com.example.tkg_studysupport.repository.AccountRepository;
import com.example.tkg_studysupport.exception.AccountNotFoundException;
import com.example.tkg_studysupport.exception.CannotRegisterStudyLogException;
import com.example.tkg_studysupport.exception.CommunityNotFoundException;
import com.example.tkg_studysupport.form.StudyLogRegisterForm;
import com.example.tkg_studysupport.repository.CommunityRepository;
import com.example.tkg_studysupport.service.StudyLogService;

import jakarta.validation.Valid;

@Controller
public class BoardController {

    private final CommunityRepository communityRepository;
    private final StudyLogService studyLogService;
    private final AccountRepository accountRepository;

    public BoardController(
            CommunityRepository communityRepository,
            StudyLogService studyLogService,
            AccountRepository accountRepository
    ) {
        this.communityRepository = communityRepository;
        this.studyLogService = studyLogService;
        this.accountRepository = accountRepository;
    }

    /** 掲示板を表示する。 */
    @GetMapping("/communities/{communityId}/board")
    public String showBoard(
            @PathVariable(name = "communityId") Long communityId,
            Authentication authentication,
            Model model
    ) {
        return renderBoard(communityId, authentication.getName(), model);
    }

    /* ModelAttributeはHTMLの入力データを受け取り, Javaのオブジェクトに変換する. */
    /* RedirectAttributeはリダイレクト先の画面にデータを渡す. */
    /** 勉強記録を登録する。 */
    @PostMapping("/communities/{communityId}/board")
    public String registerStudyLog(
            @PathVariable(name = "communityId") Long communityId,
            @ModelAttribute("studyLogRegisterForm")
            @Valid StudyLogRegisterForm form,
            BindingResult bindingResult,
            Authentication authentication,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        /* 入力値エラーチェック */
        if (bindingResult.hasErrors()) {
            model.addAttribute("openStudyLogModal", true);
            return renderBoard(communityId, authentication.getName(), model);
        }

        String loginId = authentication.getName();

        try {
            studyLogService.registerStudyLog(
                    loginId,
                    communityId,
                    form
            );
        } catch (CannotRegisterStudyLogException e) {
            bindingResult.reject(
                    "cannotRegister",
                    e.getMessage()
            );

            model.addAttribute("openStudyLogModal", true);

            /* 失敗時は掲示板の表示に必要なデータをModelへ追加する関数を呼び出す. */
            return renderBoard(communityId, authentication.getName(), model);
        }

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "勉強時間を登録しました。"
        );

        return "redirect:/communities/" + communityId + "/board";
    }

    /**
     * 掲示板の表示に必要なデータをModelへ追加する。
     * GET表示時とPOST失敗時の両方で利用する。
     */
    private String renderBoard(
            Long communityId,
            String loginId,
            Model model
    ) {
        Community community = communityRepository
                .findById(communityId)
                .orElseThrow(() -> new CommunityNotFoundException(
                        "コミュニティが見つかりません。"
                ));

        if (!model.containsAttribute("studyLogRegisterForm")) {
            StudyLogRegisterForm form = new StudyLogRegisterForm();
            form.setStudiedOn(LocalDate.now());

            model.addAttribute(
                    "studyLogRegisterForm",
                    form
            );
        }

        Account account = accountRepository
                .findByLoginId(loginId)
                .orElseThrow(() -> new AccountNotFoundException(
                        "アカウントが見つかりません。"
                ));
        
        model.addAttribute("role", account.getRole());

        model.addAttribute("community", community);

        model.addAttribute(
                "studyLogs",
                studyLogService.findStudyLogsByCommunity(communityId)
        );

        return "board";
    }
}