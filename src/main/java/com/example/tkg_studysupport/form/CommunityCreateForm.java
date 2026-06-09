package com.example.tkg_studysupport.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CommunityCreateForm {

    public CommunityCreateForm(){}

    @NotBlank
    @Size(max = 50, message="コミュニティ名は50文字以下で入力してください。")
    private String communityName;

    @NotBlank
    @Size(min = 8, max = 50, message = "パスワードは8文字以上50文字以下で入力してください。")
    private String joinPassword;

    @NotBlank
    @Size(min = 8, max = 50, message = "確認用パスワードは8文字以上50文字以下で入力してください。")
    private String joinPasswordConfirmation;

    public String getCommunityName() {
        return communityName;
    }

    public String getJoinPassword() {
        return joinPassword;
    }

    public String getJoinPasswordConfirmation() {
        return joinPasswordConfirmation;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public void setJoinPassword(String joinPassword) {
        this.joinPassword = joinPassword;
    }

    public void setJoinPasswordConfirmation(String joinPasswordConfirmation) {
        this.joinPasswordConfirmation = joinPasswordConfirmation;
    }

}
