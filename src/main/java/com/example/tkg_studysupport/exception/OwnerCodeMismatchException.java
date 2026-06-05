package com.example.tkg_studysupport.exception;

/**
 * 登録しようとした講師用コードが、一致しない例外。
 */
public class OwnerCodeMismatchException extends RuntimeException {

    /**
     * エラーメッセージを指定して例外を生成する。
     *
     * @param message エラーメッセージ
     */
    public OwnerCodeMismatchException(String message) {
        super(message);
    }
}
