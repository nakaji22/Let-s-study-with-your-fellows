package com.example.tkg_studysupport.exception;

/**
 * 入力されたパスワードと確認用パスワードが一致しない場合に発生する例外。
 */
public class PasswordMismatchException extends RuntimeException {

    /**
     * エラーメッセージを指定して例外を生成する。
     *
     * @param message エラーメッセージ
     */
    public PasswordMismatchException(String message) {
        super(message);
    }
}