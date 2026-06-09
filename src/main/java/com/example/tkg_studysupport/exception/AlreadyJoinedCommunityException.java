package com.example.tkg_studysupport.exception;

public class AlreadyJoinedCommunityException extends RuntimeException{
    /**
     * エラーメッセージを指定して例外を生成する。
     *
     * @param message エラーメッセージ
     */
    public AlreadyJoinedCommunityException(String message) {
        super(message);
    }
}
