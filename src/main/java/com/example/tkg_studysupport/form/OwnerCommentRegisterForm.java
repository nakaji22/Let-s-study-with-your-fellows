package com.example.tkg_studysupport.form;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class OwnerCommentRegisterForm {
    
    /** コメントの内容。 */
    @NotBlank(message = "コメントを入力してください。")
    @Size(min = 1, max = 1024, message = "コメントは1024文字以下で入力してください。")
    @Column(name= "comment", nullable = false, length = 1024)
    private String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
