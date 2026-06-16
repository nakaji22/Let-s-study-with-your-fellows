package com.example.tkg_studysupport.service;

import org.springframework.stereotype.Service;

import com.example.tkg_studysupport.entity.Account;
import com.example.tkg_studysupport.entity.Community;
import com.example.tkg_studysupport.entity.OwnerComment;
import com.example.tkg_studysupport.entity.OwnerProfile;
import com.example.tkg_studysupport.entity.StudyLog;
import com.example.tkg_studysupport.exception.AccountNotFoundException;
import com.example.tkg_studysupport.exception.CommunityNotFoundException;
import com.example.tkg_studysupport.exception.OwnerProfileNotFoundException;
import com.example.tkg_studysupport.form.OwnerCommentRegisterForm;
import com.example.tkg_studysupport.repository.AccountRepository;
import com.example.tkg_studysupport.repository.CommunityRepository;
import com.example.tkg_studysupport.repository.OwnerCommentRepository;
import com.example.tkg_studysupport.repository.OwnerProfileRepository;
import com.example.tkg_studysupport.repository.StudyLogRepository;

@Service
public class OwnerCommentService {
    
    private final StudyLogRepository studyLogRepository;
    private final AccountRepository accountRepository;
    private final OwnerProfileRepository ownerProfileRepository;
    private final CommunityRepository communityRepository;
    private final OwnerCommentRepository ownerCommentRepository;

    public OwnerCommentService(
            StudyLogRepository studyLogRepository,
            AccountRepository accountRepository,
            OwnerProfileRepository ownerProfileRepository,
            CommunityRepository communityRepository,
            OwnerCommentRepository ownerCommentRepository
    ) {
        this.studyLogRepository = studyLogRepository;
        this.accountRepository = accountRepository;
        this.ownerProfileRepository = ownerProfileRepository;
        this.communityRepository = communityRepository;
        this.ownerCommentRepository = ownerCommentRepository;
    }

    /* コメントの登録を行うメソッド。 */
    public OwnerComment registerComment(String loginId,
                                        Long communityId,
                                        StudyLog studyLog,
                                        OwnerCommentRegisterForm form
    ){
        Account account = accountRepository
                .findByLoginId(loginId)
                .orElseThrow(() -> new AccountNotFoundException(
                        "アカウントが見つかりません。"
                ));

        OwnerProfile owner = ownerProfileRepository
                .findByAccount(account)
                .orElseThrow(() -> new OwnerProfileNotFoundException(
                        "講師プロフィールが見つかりません。"
                ));

        Community community = communityRepository
                .findById(communityId)
                .orElseThrow(() -> new CommunityNotFoundException(
                        "コミュニティが見つかりません。"
                ));

        if (!community.isActive()) {
            throw new IllegalStateException(
                    "コミュニティはアクティブではありません。"
            );
        }

        OwnerComment comment = new OwnerComment(studyLog, owner, form.getComment());

        return ownerCommentRepository.save(comment);
    }

}
