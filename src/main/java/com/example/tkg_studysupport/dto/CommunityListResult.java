package com.example.tkg_studysupport.dto;

import java.util.List;

import com.example.tkg_studysupport.entity.Community;

public class CommunityListResult {

    private List<Community> joinedCommunities;
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
