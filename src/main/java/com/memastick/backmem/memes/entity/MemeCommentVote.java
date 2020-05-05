package com.memastick.backmem.memes.entity;

import com.memastick.backmem.base.AbstractEntity;
import com.memastick.backmem.memetick.entity.Memetick;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "memes_comment_vote")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
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
