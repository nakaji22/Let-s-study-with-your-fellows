package com.example.tkg_studysupport.dto;

import java.util.List;

import com.example.tkg_studysupport.entity.Community;

/** 参加済みコミュニティと参加可能コミュニティをそれぞれ格納するためのクラス。 */
public class CommunityListResult {

    /** 参加済みコミュニティ。 */
    private List<Community> joinedCommunities;

    /** 参加可能コミュニティ。 */
    private List<Community> availableCommunities;

    public CommunityListResult(List<Community> joinedCommunities, List<Community> availableCommunities){
        this.joinedCommunities = joinedCommunities;
        this.availableCommunities = availableCommunities;
    }

    public List<Community> getAvailableCommunities() {
        return availableCommunities;
    }

    public List<Community> getJoinedCommunities() {
        return joinedCommunities;
    }
}
