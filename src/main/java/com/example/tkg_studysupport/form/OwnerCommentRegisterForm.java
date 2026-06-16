package com.example.tkg_studysupport.form;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

public class OwnerCommentRegisterForm {
    
    /** コメントの内容。 */
    @NotBlank
    @Column(name= "comment", nullable = false, length = 1024)
    private String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
