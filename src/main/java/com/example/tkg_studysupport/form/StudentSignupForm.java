package com.example.tkg_studysupport.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import com.example.tkg_studysupport.entity.Grade;

/** 生徒登録画面から受け取る入力値をまとめる。 */
public class StudentSignupForm {
    /**
     * Spring MVCがフォーム入力値を格納するために使用する引数なしコンストラクタ。
     */
    public StudentSignupForm(){}

    /** ログインID。 */
    @NotBlank(message = "IDを入力してください。")
    @Size(max = 50, message = "IDは50文字以下で入力してください。")
    private String loginId;

    /** パスワード。 */
    @NotBlank(message = "パスワードを入力してください。")
    @Size(min = 8, max = 50, message = "パスワードは8文字以上50文字以下で入力してください。")
    private String password;

    /** 確認用パスワード。DBには保存しない。 */
    @NotBlank(message = "確認用パスワードを入力してください。")
    private String passwordComfirmation;

    /** 表示名。 */
    @NotBlank(message = "ユーザー名を入力してください。")
    @Size(max = 50, message = "ユーザー名は50文字以下で入力してください。")
    private String displayName;

    /** 学年。 */
    @NotNull(message = "学年を選択してください。")
    private Grade grade;


    /* Getterの定義 */
    public String getLoginId(){
        return this.loginId;
    }

    public String getPassword(){
        return this.password;
    }

    public String getPasswordComfirmation(){
        return this.passwordComfirmation;
    }

    public String getDisplayName(){
        return this.displayName;
    }

    public Grade getGrade(){
        return this.grade;
    }

    /* Setterの定義 */
    public void setLoginId(String loginId){
        this.loginId = loginId;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setPasswordComfimation(String passwordComfirmation){
        this.passwordComfirmation = passwordComfirmation;
    }

    public void setDisplayName(String displayName){
        this.displayName = displayName;
    }

    public void setOwnerRegistrationCode(Grade grade){
        this.grade = grade;
    }

}