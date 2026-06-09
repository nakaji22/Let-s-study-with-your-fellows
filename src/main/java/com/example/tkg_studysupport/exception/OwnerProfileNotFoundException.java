package com.example.tkg_studysupport.exception;

public class OwnerProfileNotFoundException extends RuntimeException {
    /**
     * エラーメッセージを指定して例外を生成する。
     *
     * @param message エラーメッセージ
     */
    public OwnerProfileNotFoundException(String message) {
        super(message);
    }
}
