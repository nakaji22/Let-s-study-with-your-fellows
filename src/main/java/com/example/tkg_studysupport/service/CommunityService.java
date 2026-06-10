package com.example.tkg_studysupport.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tkg_studysupport.dto.CommunityListResult;
import com.example.tkg_studysupport.entity.Account;
import com.example.tkg_studysupport.entity.Community;
import com.example.tkg_studysupport.entity.CommunityMembership;
import com.example.tkg_studysupport.entity.OwnerProfile;
import com.example.tkg_studysupport.entity.StudentProfile;
import com.example.tkg_studysupport.exception.AccountNotFoundException;
import com.example.tkg_studysupport.exception.CommunityNotFoundException;
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

/** コミュニティの作成、検索などを行うService層。 */
@Service
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

    /** コミュニティの作成を行う。 */
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

    /** コミュニティへの参加を行う。 */
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
                "生徒プロフィールが見つかりません。"
            ));

        Community community = communityRepository
            .findById(form.getCommunityId())
            .orElseThrow(() -> new CommunityNotFoundException(
                "コミュニティが見つかりません。"
            ));

        if (!community.isActive()) {
            throw new IllegalStateException("コミュニティはアクティブではありません。");
        }

        /* パスワード検証. */
        validatePasswordMatches(form.getJoinPassword(), community.getJoinPasswordHash());
        
        Optional<CommunityMembership> optionalMembership = communityMembershipRepository
            .findByCommunityAndStudent(community, student);

        /* 初参加. */
        if(optionalMembership.isEmpty()){
           CommunityMembership createdCommunityMembership = new CommunityMembership(community, student);
           CommunityMembership savedCommunityMembership = communityMembershipRepository.save(createdCommunityMembership);
           return savedCommunityMembership;
        }

        CommunityMembership membership = optionalMembership.get();

        /* 参加済み. */
        if (membership.isActive()) {
            return membership;
        }        
        /* メンバーシップを最有効化. */    
        else{
            membership.reactivate();
            CommunityMembership savedCommunityMembership = communityMembershipRepository.save(membership);
            return savedCommunityMembership;           
        }

    }

    //** パスワードと確認用パスワードの一致確認を行う。 */
    private void validatePasswordMatches(String password, String passwordConfirmation){
        if(!passwordEncoder.matches(password, passwordConfirmation)){
            throw new PasswordMismatchException("パスワードが違います。");
        }
    }

    /* readOnly=trueにより読み取り専用のトランザクション処理を宣言する。検索のみのメソッドに適用することで役割を明確化する。 */
    //* 有効な未参加コミュニティの一覧を返す。 */
    @Transactional(readOnly = true)
    public List<Community> searchAllCommunities(String keyword) {
        if(keyword == null){
            List<Community> allCommunityList = communityRepository.findAll();
            return allCommunityList;
        }
        String strippedKeyword = keyword.strip();
        List<Community> communityList= communityRepository.findByCommunityNameContainingIgnoreCaseAndActiveTrue(strippedKeyword);
        return communityList;
    }

    /** 特定生徒が参加しているコミュニティ一覧を返す。 */
    @Transactional(readOnly = true)
    public List<CommunityMembership> findJoinedCommunities(String loginId) {
        Account account = accountRepository.findByLoginId(loginId)
                    .orElseThrow(() -> new AccountNotFoundException(
                    "アカウントが見つかりません。"
                    ));


        StudentProfile student = studentProfileRepository
            .findByAccount(account)
            .orElseThrow(() -> new StudentProfileNotFoundException(
                "生徒情報が見つかりません。"
            ));


        List<CommunityMembership> communityList = communityMembershipRepository.findByStudentAndActiveTrue(student); 

        return communityList;
    }

    //* 特定コミュニティに参加している有効な生徒一覧を返す。 */
    @Transactional(readOnly = true)
    public List<CommunityMembership> findActiveMembers(String loginId, Long communityId) {
        Account account = accountRepository.findByLoginId(loginId)
                    .orElseThrow(() -> new AccountNotFoundException(
                    "アカウントが見つかりません。"
                    ));

        Optional<OwnerProfile> optionalOwner = ownerProfileRepository.findByAccount(account);
        
        if(optionalOwner.isEmpty()){
            throw new OwnerProfileNotFoundException("講師情報が見つかりません。");
        }

        Community community = communityRepository
            .findById(communityId)
            .orElseThrow(() -> new StudentProfileNotFoundException(
                "コミュニティが見つかりません。"
            ));

        List<CommunityMembership> communityMembershipList = communityMembershipRepository.findByCommunityAndActiveTrue(community);
        return communityMembershipList;
    }

    /** 
     * すべての有効なコミュニティから、
     * 特定生徒が参加しているコミュニティと参加可能なコミュニティを選別する。
     */
    @Transactional(readOnly = true)
    public CommunityListResult searchResultCommunities(
        List<Community> communities,
        String loginId
    ) {
        Account account = accountRepository
            .findByLoginId(loginId)
            .orElseThrow(() -> new AccountNotFoundException(
                "アカウントが見つかりません。"
            ));

        StudentProfile student = studentProfileRepository
            .findByAccount(account)
            .orElseThrow(() -> new StudentProfileNotFoundException(
                "生徒情報が見つかりません。"
            ));

        List<CommunityMembership> joinedCommunityMemberships =
            communityMembershipRepository.findByStudentAndActiveTrue(student);

        List<Community> joinedCommunities = new ArrayList<>();

        /*
        * 参加済みコミュニティのIDを保存する。
        * Communityオブジェクトそのものではなく、
        * DB上で一意なcommunityIdを使って比較する。
        */
        Set<Long> joinedCommunityIds = new HashSet<>();

        for (CommunityMembership membership : joinedCommunityMemberships) {
            /*
            * membershipやcommunityを1つずつ判定し、
            * 問題があればその回の残りの処理をスキップする。
            */
            if (membership == null) {
                continue;
            }

            Community community = membership.getCommunity();

            if (community == null) {
                continue;
            }

            if (!community.isActive()) {
                continue;
            }

            Long communityId = community.getCommunityId();

            if (communityId == null) {
                continue;
            }

            /*
            * 同じコミュニティが重複して追加されることも防ぐ。
            */
            if (joinedCommunityIds.add(communityId)) {
                joinedCommunities.add(community);
            }
        }

        List<Community> availableCommunities = communities.stream()
            .filter(community -> community != null)
            .filter(Community::isActive)
            .filter(community -> community.getCommunityId() != null)
            .filter(community ->
                !joinedCommunityIds.contains(community.getCommunityId())
            )
            .collect(Collectors.toList());

        return new CommunityListResult(
            joinedCommunities,
            availableCommunities
        );
    }

}
