package com.memastick.backmem.memes.entity;

import com.memastick.backmem.base.AbstractEntity;
import com.memastick.backmem.memetick.entity.Memetick;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "memes_comment_vote")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemeCommentVote extends AbstractEntity {

    @NaturalId
    @Column(nullable = false)
    private UUID commentId;

    @NaturalId
    @Column(nullable = false)
    private UUID memetickId;

    @Column(nullable = false)
    private boolean vote;

    public MemeCommentVote(MemeComment comment, Memetick memetick) {
        this.commentId = comment.getId();
        this.memetickId = memetick.getId();
    }
}
