package com.example.tkg_studysupport.entity;

/**
 * 生徒の学年を表す列挙型。
 * <p>
 * 生徒アカウントの学年登録と、
 * コミュニティ参加時の対象学年判定に使用する。
 * </p>
 */
public enum Grade {
    /** 小学1年生。 */
    ELEMENTARY_1,
    /** 小学2年生。 */
    ELEMENTARY_2,
    /** 小学3年生。 */
    ELEMENTARY_3,
    /** 小学4年生。 */
    ELEMENTARY_4,
    /** 小学5年生。 */
    ELEMENTARY_5,
    /** 小学6年生。 */
    ELEMENTARY_6,
    /** 中学1年生。 */
    JUNIOR_HIGH_1,
    /** 中学2年生。 */
    JUNIOR_HIGH_2,
    /** 中学3年生。 */
    JUNIOR_HIGH_3,
    /** 高校1年生。 */
    HIGH_SCHOOL_1,
    /** 高校2年生。 */
    HIGH_SCHOOL_2,
    /** 高校3年生。 */
    HIGH_SCHOOL_3
}