package com.example.tkg_studysupport.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/** コミュニティ作成画面から入力値を受け取るフォーム。 */
public class CommunityCreateForm {
    /**
     * Spring MVCがフォーム入力値を格納するために使用する引数なしコンストラクタ。
     */   
    public CommunityCreateForm(){}

    /** コミュニティ名。 */
    @NotBlank
    @Size(max = 50, message="コミュニティ名は50文字以下で入力してください。")
    private String communityName;

    /** コミュニティパスワード。 */
    @NotBlank
    @Size(min = 8, max = 50, message = "パスワードは8文字以上50文字以下で入力してください。")
    private String joinPassword;

    /** 確認用コミュニティパスワード。 */
    @NotBlank
    @Size(min = 8, max = 50, message = "確認用パスワードは8文字以上50文字以下で入力してください。")
    private String joinPasswordConfirmation;

    /* Getterの定義。 */
    public String getCommunityName() {
        return communityName;
    }

    public String getJoinPassword() {
        return joinPassword;
    }

    public String getJoinPasswordConfirmation() {
        return joinPasswordConfirmation;
    }

    /** Setterの定義。 */
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
