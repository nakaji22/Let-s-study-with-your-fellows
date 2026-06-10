package com.example.tkg_studysupport.controller;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.tkg_studysupport.entity.Account;
import com.example.tkg_studysupport.entity.AccountRole;
import com.example.tkg_studysupport.entity.Community;
import com.example.tkg_studysupport.entity.CommunityMembership;
import com.example.tkg_studysupport.entity.StudentProfile;
import com.example.tkg_studysupport.exception.AccountNotFoundException;
import com.example.tkg_studysupport.exception.CommunityMembershipNotFoundException;
import com.example.tkg_studysupport.exception.CommunityNotFoundException;
import com.example.tkg_studysupport.exception.StudentProfileNotFoundException;
import com.example.tkg_studysupport.repository.AccountRepository;
import com.example.tkg_studysupport.repository.CommunityMembershipRepository;
import com.example.tkg_studysupport.repository.CommunityRepository;
import com.example.tkg_studysupport.repository.StudentProfileRepository;

@Controller
@RequestMapping("/communities")
public class CommunityBoardController {

    private final AccountRepository accountRepository;
    private final CommunityRepository communityRepository;
    private final CommunityMembershipRepository communityMembershipRepository;
    private final StudentProfileRepository studentProfileRepository;

    public CommunityBoardController(AccountRepository accountRepository, CommunityRepository communityRepository, 
                                    StudentProfileRepository studentProfileRepository, CommunityMembershipRepository communityMembershipRepository){
        this.accountRepository = accountRepository;
        this.communityRepository = communityRepository;
        this.studentProfileRepository = studentProfileRepository;
        this.communityMembershipRepository = communityMembershipRepository;
    }
    
    @GetMapping("/{communityId}/board")
    public String displayBoard(
        @PathVariable(name = "communityId") Long communityId,
        Authentication authentication,
        Model model
    ){
        String loginId = authentication.getName();

        Account account = accountRepository.findByLoginId(loginId)
                    .orElseThrow(() -> new AccountNotFoundException(
                    "アカウントが見つかりません。"
                    ));

        Optional<Community> optionalCommunity = communityRepository.findById(communityId);

        if(optionalCommunity.isEmpty()){
            return "redirect:/home";
        }

        Community community = optionalCommunity.get();
        model.addAttribute("community", community);

        if(!community.isActive()){
            throw new CommunityNotFoundException("コミュニティが削除済みです。");
        }

        AccountRole role = account.getRole();

        /* role == STUDENTの場合はメンバーシップの対応関係を確認し、存在すれば掲示板ページへ遷移. */
        if(role == AccountRole.STUDENT){
            StudentProfile student = studentProfileRepository
                .findByAccount(account)
                .orElseThrow(() -> new StudentProfileNotFoundException(
                    "生徒情報が見つかりません。"
                ));
            
            CommunityMembership membership = communityMembershipRepository
                .findByCommunityAndStudent(community, student)
                .orElseThrow(() -> new CommunityMembershipNotFoundException(
                    "メンバーシップが見つかりません。"
                ));
            
            /* メンバーシップが見つからなければhomeへリダイレクト. */
            if(!membership.isActive()){
                return "redirect:/home";
            }
        }

        /* role == OWNERの場合は!community.isActive()を抜ければ掲示板ページを無条件に表示. */
        return "board";
    }

}
