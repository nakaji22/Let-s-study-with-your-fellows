package com.example.tkg_studysupport.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.tkg_studysupport.dto.CommunityListResult;
import com.example.tkg_studysupport.entity.Account;
import com.example.tkg_studysupport.entity.AccountRole;
import com.example.tkg_studysupport.entity.Community;
import com.example.tkg_studysupport.exception.AccountNotFoundException;
import com.example.tkg_studysupport.form.CommunityJoinForm;
import com.example.tkg_studysupport.repository.AccountRepository;
import com.example.tkg_studysupport.service.CommunityService;

@Controller
public class HomeController {

    private final CommunityService communityService;
    private final AccountRepository accountRepository;

    public HomeController(CommunityService communityService, AccountRepository accountRepository){
        this.communityService = communityService;
        this.accountRepository = accountRepository;
    }

    @GetMapping("/home")
    public String displayHome(
        @RequestParam(name = "keyword", required = false) String keyword,
        Authentication authentication,
        Model model
    ) {
        String loginId = authentication.getName();
        Account account = accountRepository.findByLoginId(loginId)
                    .orElseThrow(() -> new AccountNotFoundException(
                    "アカウントが見つかりません。"
                    ));
        AccountRole role = account.getRole();

        if(role == AccountRole.STUDENT){
            List<Community> allCommunities = communityService.searchAllCommunities(keyword);
            CommunityListResult resultCommunities = communityService.searchResultCommunities(allCommunities, loginId);
            List<Community> joinedCommunities = resultCommunities.getJoinedCommunities();
            List<Community> availableCommunities = resultCommunities.getAvailableCommunities();

            model.addAttribute("joinedCommunities", joinedCommunities);
            model.addAttribute("availableCommunities", availableCommunities);
            model.addAttribute("role", role);
        }

        else if(role == AccountRole.OWNER){
            List<Community> ownerCommunities = communityService.searchAllCommunities(keyword);
            model.addAttribute("ownerCommunities", ownerCommunities);
            model.addAttribute("role", role);
        }
        model.addAttribute("keyword", keyword == null ? "" : keyword);

        return "home";
    }

    /* @PathVariable:URLパスの一部を変数として受け取るための機能. */
    @PostMapping("/communities/{communityId}/join")
    public String joinCommunity(
        @PathVariable(name = "communityId") Long communityId,
        @RequestParam(name = "joinPassword") String joinPassword,
        Authentication authentication
    ) {
        String loginId = authentication.getName();
        CommunityJoinForm form = new CommunityJoinForm(communityId, joinPassword);
        communityService.joinCommunity(loginId, form);
        
        return "redirect:/communities/{communityId}/board";
    }

    @GetMapping("/")
    public String redirectToLogin() {
        return "redirect:/login";
    }
} 