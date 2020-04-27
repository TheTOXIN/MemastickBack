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
@Table(
    name = "memes_comment_vote",
    uniqueConstraints = @UniqueConstraint(columnNames = {"comment_id", "memetick_id"})
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MemeCommentVote extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private MemeComment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Memetick memetick;

    @Column(nullable = false)
    private boolean vote;

    public MemeCommentVote(MemeComment comment, Memetick memetick) {
        this.comment = comment;
        this.memetick = memetick;
    }
}
