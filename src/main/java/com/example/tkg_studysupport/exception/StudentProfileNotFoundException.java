package com.example.tkg_studysupport.exception;

public class StudentProfileNotFoundException extends RuntimeException {
    /**
     * エラーメッセージを指定して例外を生成する。
     *
     * @param message エラーメッセージ
     */
    public StudentProfileNotFoundException(String message) {
        super(message);
    }
}
