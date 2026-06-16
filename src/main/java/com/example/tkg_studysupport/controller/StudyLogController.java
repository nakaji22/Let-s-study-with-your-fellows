package com.example.tkg_studysupport.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.tkg_studysupport.entity.Community;
import com.example.tkg_studysupport.entity.OwnerComment;
import com.example.tkg_studysupport.entity.StudyLog;
import com.example.tkg_studysupport.exception.CommunityNotFoundException;
import com.example.tkg_studysupport.exception.StudyLogNotFoundException;
import com.example.tkg_studysupport.repository.CommunityRepository;
import com.example.tkg_studysupport.repository.OwnerCommentRepository;
import com.example.tkg_studysupport.repository.StudyLogRepository;
import com.example.tkg_studysupport.service.StudyLogService;

@Controller
public class StudyLogController {

    private final CommunityRepository communityRepository;
    private final StudyLogRepository studyLogRepository;
    private final OwnerCommentRepository ownerCommentRepository;

    public StudyLogController(
            CommunityRepository communityRepository,
            StudyLogRepository studyLogRepository,
            OwnerCommentRepository ownerCommentRepository
    ) {
        this.communityRepository = communityRepository;
        this.studyLogRepository = studyLogRepository;
        this.ownerCommentRepository = ownerCommentRepository;
    }

    @GetMapping("/communities/{communityId}/board/{studyLogId}/studylog")
    public String displayStudyLog(
        @PathVariable(name = "communityId") Long communityId,
        @PathVariable(name = "studyLogId") Long studyLogId,
        Model model
    ){
        Community community = communityRepository
                .findById(communityId)
                .orElseThrow(() -> new CommunityNotFoundException(
                        "コミュニティが見つかりません。"
                ));
        
        /* orElseThrowはOptional型に対してのみ使用可能. */
        StudyLog studyLog = studyLogRepository
                .findByStudyLogId(studyLogId)
                .orElseThrow(() -> new StudyLogNotFoundException(
                        "勉強記録が見つかりません。"
                ));

        List<OwnerComment> comments = ownerCommentRepository
                          .findByCommentedInOrderByCommentedAtAsc(studyLog);

        model.addAttribute("community", community);
        model.addAttribute("studyLog", studyLog);
        model.addAttribute("comments", comments);
        

        return "/communities/" + communityId + "/board/" + studyLogId + "/studylog";
    }

}
