package com.example.tkg_studysupport.exception;

/**
 * 登録しようとしたログインIDが、すでに使用されている場合に発生する例外。
 */
public class DuplicateLoginIdException extends RuntimeException {

    /**
     * エラーメッセージを指定して例外を生成する。
     *
     * @param message エラーメッセージ
     */
    public DuplicateLoginIdException(String message) {
        super(message);
    }
}