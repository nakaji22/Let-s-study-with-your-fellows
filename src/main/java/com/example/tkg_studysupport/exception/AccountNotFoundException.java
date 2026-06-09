package com.example.tkg_studysupport.exception;

public class AccountNotFoundException extends RuntimeException {
    /**
     * エラーメッセージを指定して例外を生成する。
     *
     * @param message エラーメッセージ
     */
    public AccountNotFoundException(String message) {
        super(message);
    }
}
