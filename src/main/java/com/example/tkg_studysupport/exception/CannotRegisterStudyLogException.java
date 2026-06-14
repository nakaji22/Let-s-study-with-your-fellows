package com.example.tkg_studysupport.exception;

public class CannotRegisterStudyLogException extends RuntimeException{
    /**
     * エラーメッセージを指定して例外を生成する。
     *
     * @param message エラーメッセージ
     */
    public CannotRegisterStudyLogException(String message) {
        super(message);
    }
}
