package com.example.tkg_studysupport.exception;

public class StudyLogNotFoundException extends RuntimeException{
    /**
     * エラーメッセージを指定して例外を生成する。
     *
     * @param message エラーメッセージ
     */
    public StudyLogNotFoundException(String message) {
        super(message);
    }
}
