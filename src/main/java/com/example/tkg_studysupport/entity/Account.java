package com.example.tkg_studysupport.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;  
import jakarta.validation.constraints.Size;

/**
 * 生徒と講師で共通する情報。
 * <p>
 * 下記の内容を管理し区別する。
 * ・誰がログインするか
 * ・どの種類のアカウントか
 * ・ログイン可能な状態か
 */
@Entity
@Table(name="accounts")
public class Account {
    /**
     * Hibernateが使用する引数なしコンストラクタ。
     */
    protected Account(){}

    /** アカウント登録用コンストラクタ */
    public Account(String loginId, String passwordHash, String displayName, AccountRole role){
        this.loginId = loginId;
        this.passwordHash = passwordHash;
        this.displayName = displayName;
        this.role = role;
        this.enabled = true;
        this.createdAt = LocalDateTime.now();
    }

    /** DBが自動採番するID。 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /* @Column：DB側の判定. @Size：Java側の判定. */
    /** 生徒or講師が自身で決定するログインID。 */
    @NotBlank
    @Column(name="login_id", nullable = false, unique = true, length = 50)
    @Size(max = 50, message = "IDは50文字以下で設定してください。")
    private String loginId;

    /** 
     * ハッシュ済みパスワード。
     * DBにはパスワードの平文を保存しない。
     */
    @NotBlank
    @Column(name="password_hash", nullable = false, length = 50)
    @Size(max = 255)
    private String passwordHash;

    /** プロフィールに表示する名前。 */
    @NotBlank
    @Column(name="display_name", nullable = false, length = 50)
    @Size(max = 50, message = "ユーザー名は50文字以下で設定してください。")
    private String displayName;

    /** 生徒or講師の権限設定。 */
    @NotNull
    @Column(name="role", nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountRole role;

    /** 退会or退職済み生徒or講師の管理用フィールド。 */
    @Column(name="enabled", nullable = false)
    private boolean enabled;

    /** 登録日時管理用フィールド。 */
    @NotNull
    @Column(name="created_at", nullable = false)
    @NotNull
    private LocalDateTime createdAt;

    public Long getId(){
        return this.id;
    }

    public String getLoginId(){
        return this.loginId;
    }

    public String getPasswordHash(){
        return this.passwordHash;
    }

    public String getDisplayName(){
        return this.displayName;
    }

    public AccountRole getRole(){
        return this.role;
    }

    public boolean isEnabled(){
        return this.enabled;
    }

    public LocalDateTime getCreatedAt(){
        return this.createdAt;
    }
}
