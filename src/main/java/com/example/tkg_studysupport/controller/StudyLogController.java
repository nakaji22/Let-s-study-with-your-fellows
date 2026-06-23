package com.example.tkg_studysupport.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.tkg_studysupport.entity.Account;
import com.example.tkg_studysupport.entity.Community;
import com.example.tkg_studysupport.entity.OwnerComment;
import com.example.tkg_studysupport.entity.StudyLog;
import com.example.tkg_studysupport.exception.AccountNotFoundException;
import com.example.tkg_studysupport.exception.CommunityNotFoundException;
import com.example.tkg_studysupport.exception.StudyLogNotFoundException;
import com.example.tkg_studysupport.form.OwnerCommentRegisterForm;
import com.example.tkg_studysupport.repository.AccountRepository;
import com.example.tkg_studysupport.repository.CommunityRepository;
import com.example.tkg_studysupport.repository.OwnerCommentRepository;
import com.example.tkg_studysupport.repository.StudyLogRepository;
import com.example.tkg_studysupport.service.OwnerCommentService;

import jakarta.validation.Valid;

@Controller
public class StudyLogController {

        private final CommunityRepository communityRepository;
        private final StudyLogRepository studyLogRepository;
        private final OwnerCommentRepository ownerCommentRepository;
        private final OwnerCommentService ownerCommentService;
        private final AccountRepository accountRepository;

        public StudyLogController(
                CommunityRepository communityRepository,
                StudyLogRepository studyLogRepository,
                OwnerCommentRepository ownerCommentRepository,
                OwnerCommentService ownerCommentService,
                AccountRepository accountRepository
        ) {
        this.communityRepository = communityRepository;
        this.studyLogRepository = studyLogRepository;
        this.ownerCommentRepository = ownerCommentRepository;
        this.ownerCommentService = ownerCommentService;
        this.accountRepository = accountRepository;
        }

        /** 勉強記録を表示するコントローラー。 */
        @GetMapping("/communities/{communityId}/board/studylog/{studyLogId}")
        public String displayStudyLog(
        @PathVariable(name = "communityId") Long communityId,
        @PathVariable(name = "studyLogId") Long studyLogId,
        Authentication authentication,
        Model model
        ){
        Community community = communityRepository
                .findById(communityId)
                .orElseThrow(() -> new CommunityNotFoundException(
                        "コミュニティが見つかりません。"
                ));

        /* orElseThrowはOptional型に対してのみ使用可能. */
        StudyLog studyLog = studyLogRepository
                .findByStudyLogIdWithStudentAccount(studyLogId)
                .orElseThrow(() -> new StudyLogNotFoundException(
                        "勉強記録が見つかりません。"
                ));

        Account account = accountRepository
                .findByLoginId(authentication.getName())
                .orElseThrow(() -> new AccountNotFoundException(
                        "アカウントが見つかりません。"
                ));

        List<OwnerComment> comments = ownerCommentRepository
                                .findByCommentedInOrderByCommentedAtAsc(studyLog);

        model.addAttribute("community", community);
        model.addAttribute("studyLog", studyLog);
        model.addAttribute("comments", comments);
        model.addAttribute("role", account.getRole());
        model.addAttribute("ownerCommentRegisterForm", new OwnerCommentRegisterForm());


        return "studylog";
        }

        @PostMapping("/communities/{communityId}/board/studylog/{studyLogId}/comments")
        public String postOwnerComment(
                @PathVariable(name = "communityId") Long communityId,
                @PathVariable(name = "studyLogId") Long studyLogId,
                @ModelAttribute("OwnerCommentRegisterForm")
                @Valid OwnerCommentRegisterForm form,
                BindingResult bindingResult,
                Authentication authentication,
                Model model
        ) {
        if (bindingResult.hasErrors()) {
                return displayStudyLog(
                        communityId,
                        studyLogId,
                        authentication,
                        model
                );
        }

        StudyLog studyLog = studyLogRepository
                .findByStudyLogIdWithStudentAccount(studyLogId)
                .orElseThrow(() -> new StudyLogNotFoundException(
                        "勉強記録が見つかりません。"
                ));

        ownerCommentService.registerComment(
                authentication.getName(),
                communityId,
                studyLog,
                form
        );

        return "redirect:/communities/"
                + communityId
                + "/board/studylog/"
                + studyLogId;
        }

}
