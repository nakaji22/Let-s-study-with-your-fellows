package com.example.tkg_studysupport.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CommunityJoinForm {
    
    public CommunityJoinForm(){}

    @NotNull
    private Long communityId;

    @NotBlank
    @Size(min = 8, max = 50, message = "パスワードは8文字以上50文字以下で入力してください。")
    private String joinPassword;

    @NotBlank
    @Size(min = 8, max = 50, message = "確認用パスワードは8文字以上50文字以下で入力してください。")
    private String joinPasswordConfirmation;

    public Long getCommunityId() {
        return communityId;
    }

    public String getJoinPassword() {
        return joinPassword;
    }

    public String getJoinPasswordConfirmation() {
        return joinPasswordConfirmation;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public void setJoinPassword(String joinPassword) {
        this.joinPassword = joinPassword;
    }

    public void setJoinPasswordConfirmation(String joinPasswordConfirmation) {
        this.joinPasswordConfirmation = joinPasswordConfirmation;
    }

}
