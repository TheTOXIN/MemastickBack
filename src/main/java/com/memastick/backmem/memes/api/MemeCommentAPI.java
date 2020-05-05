package com.memastick.backmem.memes.api;

import com.memastick.backmem.memes.entity.MemeComment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemeCommentAPI {

    private UUID commentId;
    private UUID memetickId;

    private String comment;
    private int point;

    private Boolean vote;

    public MemeCommentAPI(MemeComment entity, Boolean vote) {
        this.commentId = entity.getId();
        this.memetickId = entity.getMemetick().getId();

        this.comment = entity.getComment();
        this.point = entity.getPoint();

        this.vote = vote;
    }
}
