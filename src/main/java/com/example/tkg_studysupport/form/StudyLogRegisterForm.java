package com.example.tkg_studysupport.form;

import java.time.LocalDate;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

public class StudyLogRegisterForm {

    /** いつ勉強したか。 */
    @NotNull
    @PastOrPresent
    private LocalDate studiedOn;

    /** 何分勉強したか。 */
    @NotNull
    @Min(value = 1, message = "勉強時間は1分以上で入力してください。")
    @Max(value = 1440, message = "勉強時間は1440分以下で入力してください。")
    private Integer studyMinutes;

    /* Getterの定義 */
    public LocalDate getStudiedOn() {
        return studiedOn;
    }

    public Integer getStudyMinutes() {
        return studyMinutes;
    }

    /* Setterの定義 */
    public void setStudiedOn(LocalDate studiedOn) {
        this.studiedOn = studiedOn;
    }

    public void setStudyMinutes(Integer studyMinutes) {
        this.studyMinutes = studyMinutes;
    }
}
