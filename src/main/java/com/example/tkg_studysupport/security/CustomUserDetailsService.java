package com.example.tkg_studysupport.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.tkg_studysupport.entity.Account;
import com.example.tkg_studysupport.repository.AccountRepository;

/* UserDetailsServiceを自身の形に適合させるため、implementsにより実装する */
/** UserDetailsServiceを実装した認証用クラス。 */
@Service
public class CustomUserDetailsService implements UserDetailsService{
    private final AccountRepository accountRepository;
    
    public CustomUserDetailsService(
        AccountRepository accountRepository
    ){
        this.accountRepository = accountRepository;
    }

    /** loginIdが見つかる⇒builderによりAccount型をUserDetails型に変換して返す
     * loginIdが見つからない⇒UsernameNotFoundExceptionの例外処理
     */
    /* 自作クラスの各フィールドを、SpringSecurityの共通型であるUserDetailsに変換することで、ログイン処理を既存フレームワークに一任する */
    @Override
    public UserDetails loadUserByUsername(String username){
        Account account = accountRepository.findByLoginId(username)
            .orElseThrow(() -> new UsernameNotFoundException("入力されたIDのアカウントが見つかりません。"));

        return User.builder()
                .username(account.getLoginId())
                .password(account.getPasswordHash())
                .authorities("ROLE_" + account.getRole().name())
                .disabled(!account.isEnabled())
                .build();
    }
}
