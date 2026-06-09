package com.example.tkg_studysupport.exception;

public class CommunityMembershipNotFoundException extends RuntimeException{
    /**
     * エラーメッセージを指定して例外を生成する。
     *
     * @param message エラーメッセージ
     */
    public CommunityMembershipNotFoundException(String message) {
        super(message);
    }
}
