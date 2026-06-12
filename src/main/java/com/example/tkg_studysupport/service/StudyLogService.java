package com.example.tkg_studysupport.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.tkg_studysupport.entity.Account;
import com.example.tkg_studysupport.entity.Community;
import com.example.tkg_studysupport.entity.StudentProfile;
import com.example.tkg_studysupport.entity.StudyLog;
import com.example.tkg_studysupport.exception.AccountNotFoundException;
import com.example.tkg_studysupport.exception.CommunityMembershipNotFoundException;
import com.example.tkg_studysupport.exception.CommunityNotFoundException;
import com.example.tkg_studysupport.exception.StudentProfileNotFoundException;
import com.example.tkg_studysupport.form.StudyLogRegisterForm;
import com.example.tkg_studysupport.repository.AccountRepository;
import com.example.tkg_studysupport.repository.CommunityRepository;
import com.example.tkg_studysupport.repository.StudentProfileRepository;
import com.example.tkg_studysupport.repository.StudyLogRepository;

import jakarta.transaction.Transactional;

@Service
public class StudyLogService {

    private final StudyLogRepository studyLogRepository;
    private final AccountRepository accountRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final CommunityRepository communityRepository;

    public StudyLogService(StudyLogRepository studyLogRepository, AccountRepository accountRepository,
                           StudentProfileRepository studentProfileRepository, CommunityRepository communityRepository
    ){
        this.studyLogRepository = studyLogRepository;
        this.accountRepository = accountRepository;
        this.studentProfileRepository = studentProfileRepository;
        this.communityRepository = communityRepository;
    }

    /** 勉強記録の登録を行う。 */
    @Transactional
    public StudyLog registerStudyLog(String loginId, Long communityId, StudyLogRegisterForm form){
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
            .findById(communityId)
            .orElseThrow(() -> new CommunityNotFoundException(
                "コミュニティが見つかりません。"
            ));

        if (!community.isActive()) {
            throw new IllegalStateException("コミュニティはアクティブではありません。");
        }

        studyLogRepository.findByCommunityAndStudentAndActiveTrue(community, student)
                          .orElseThrow(() -> new CommunityMembershipNotFoundException(
                              "コミュニティが見つかりません。"));
                                            
        StudyLog studyLog = new StudyLog(student, community, form.getStudyMinutes(), form.getStudiedOn());
        
        return studyLogRepository.save(studyLog);

    }

    /** 特定のコミュニティ内の勉強記録を時系列順に返す. */
    public List<StudyLog> findStudyLogsByCommunity(Long communityId){
        Community community = communityRepository
            .findById(communityId)
            .orElseThrow(() -> new CommunityNotFoundException(
                "コミュニティが見つかりません。"
            ));
        List<StudyLog> studyLogs = studyLogRepository.findByCommunityOrderByStudiedOnAscCreatedAtAsc(community);
        return studyLogs;
    }
    
}
