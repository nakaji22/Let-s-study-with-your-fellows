package com.example.tkg_studysupport.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.tkg_studysupport.entity.Account;
import com.example.tkg_studysupport.entity.Community;
import com.example.tkg_studysupport.entity.CommunityMembership;
import com.example.tkg_studysupport.entity.OwnerProfile;
import com.example.tkg_studysupport.entity.StudentProfile;
import com.example.tkg_studysupport.exception.AccountNotFoundException;
import com.example.tkg_studysupport.exception.AlreadyJoinedCommunityException;
import com.example.tkg_studysupport.exception.OwnerProfileNotFoundException;
import com.example.tkg_studysupport.exception.PasswordMismatchException;
import com.example.tkg_studysupport.exception.StudentProfileNotFoundException;
import com.example.tkg_studysupport.form.CommunityCreateForm;
import com.example.tkg_studysupport.form.CommunityJoinForm;
import com.example.tkg_studysupport.repository.AccountRepository;
import com.example.tkg_studysupport.repository.CommunityMembershipRepository;
import com.example.tkg_studysupport.repository.CommunityRepository;
import com.example.tkg_studysupport.repository.OwnerProfileRepository;
import com.example.tkg_studysupport.repository.StudentProfileRepository;

import jakarta.transaction.Transactional;

public class CommunityService {
    private final CommunityRepository communityRepository;
    private final CommunityMembershipRepository communityMembershipRepository;
    private final AccountRepository accountRepository;
    private final OwnerProfileRepository ownerProfileRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final PasswordEncoder passwordEncoder;

    public CommunityService(
        CommunityRepository communityRepository,
        CommunityMembershipRepository communityMembershipRepository,
        AccountRepository accountRepository,
        OwnerProfileRepository ownerProfileRepository,
        StudentProfileRepository studentProfileRepository,
        PasswordEncoder passwordEncoder
    ){
        this.communityRepository = communityRepository;
        this.communityMembershipRepository = communityMembershipRepository;
        this.accountRepository = accountRepository;
        this.ownerProfileRepository = ownerProfileRepository;
        this.studentProfileRepository = studentProfileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Community createCommunity(String loginId, CommunityCreateForm form){
        Account account = accountRepository
            .findByLoginId(loginId)
            .orElseThrow(() -> new AccountNotFoundException(
                "アカウントが見つかりません。"
            ));

        OwnerProfile ownerProfile = ownerProfileRepository
            .findByAccount(account)
            .orElseThrow(() -> new OwnerProfileNotFoundException(
                "講師プロフィールが見つかりません。"
            ));
        String joinPasswordHash = passwordEncoder.encode(form.getJoinPassword());

        Community community = new Community(form.getCommunityName(), joinPasswordHash, ownerProfile);

        Community savedCommunity = communityRepository.save(community);

        return savedCommunity;
    }

    @Transactional
    public CommunityMembership joinCommunity(String loginId, CommunityJoinForm form){
        Account account = accountRepository
            .findByLoginId(loginId)
            .orElseThrow(() -> new AccountNotFoundException(
                "アカウントが見つかりません。"
            ));

        StudentProfile student = studentProfileRepository
            .findByAccount(account)
            .orElseThrow(() -> new StudentProfileNotFoundException(
                "講師プロフィールが見つかりません。"
            ));

        Community community = communityRepository
            .findById(form.getCommunityId())
            .orElseThrow(() -> new StudentProfileNotFoundException(
                "生徒プロフィールが見つかりません。"
            ));

        if (!community.isActive()) {
            throw new IllegalStateException("コミュニティはアクティブではありません。");
        }

        validatePasswordMatches(form.getJoinPassword(), form.getJoinPasswordConfirmation());
        
        Optional<CommunityMembership> optionalMembership = communityMembershipRepository
            .findByCommunityAndStudent(community, student);

        if(optionalMembership.isEmpty()){
           CommunityMembership createdCommunityMembership = new CommunityMembership(community, student);
           CommunityMembership savedCommunityMembership = communityMembershipRepository.save(createdCommunityMembership);
           return savedCommunityMembership;
        }

        CommunityMembership membership = optionalMembership.get();

        if (membership.isActive()) {
            throw new AlreadyJoinedCommunityException(
                "すでにこのコミュニティに参加しています。"
            );
        }            
        else{
            membership.EnableActive();
            CommunityMembership savedCommunityMembership = communityMembershipRepository.save(membership);
            return savedCommunityMembership;           
        }

    }

    private void validatePasswordMatches(String password, String passwordConfirmation){
        if(!Objects.equals(password, passwordConfirmation)){
            throw new PasswordMismatchException("パスワードが違います。");
        }
    }

    public void searchActiveCommunities() {
    }

    public void findJoinedCommunities() {
    }

    public void findActiveMembers() {
    }
}
