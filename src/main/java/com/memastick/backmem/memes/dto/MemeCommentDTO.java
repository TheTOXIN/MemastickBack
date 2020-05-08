package com.memastick.backmem.memes.dto;

import com.memastick.backmem.memes.entity.MemeComment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemeCommentDTO {

    private UUID memeId;
    private UUID memetickId;
    private String comment;
    private int point;

    public MemeCommentDTO(MemeComment comment) {
        this.memeId = comment.getMemeId();
        this.memetickId = comment.getMemetickId();
        this.comment = comment.getComment();
        this.point = comment.getPoint();
    }
}
