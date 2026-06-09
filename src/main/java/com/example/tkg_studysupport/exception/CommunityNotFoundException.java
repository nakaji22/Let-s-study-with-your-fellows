package com.example.tkg_studysupport.exception;

public class CommunityNotFoundException extends RuntimeException{
    /**
     * エラーメッセージを指定して例外を生成する。
     *
     * @param message エラーメッセージ
     */
    public CommunityNotFoundException(String message) {
        super(message);
    } 
}
