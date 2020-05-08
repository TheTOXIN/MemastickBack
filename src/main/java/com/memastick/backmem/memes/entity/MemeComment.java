package com.memastick.backmem.memes.entity;

import com.memastick.backmem.base.AbstractEntity;
import com.memastick.backmem.memetick.entity.Memetick;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

import java.time.ZonedDateTime;
import java.util.UUID;

import static com.memastick.backmem.main.constant.ValidConstant.MAX_TEXT_LEN;

@Entity
@Table(name = "memes_comment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MemeComment extends AbstractEntity {

    @NaturalId
    private UUID memeId;

    @NaturalId
    private UUID memetickId;

    @Column(nullable = false)
    @Length(max = MAX_TEXT_LEN)
    private String comment;

    @Column(nullable = false)
    private ZonedDateTime creating;

    @Column(nullable = false)
    private int point;

    public MemeComment(Meme meme, Memetick memetick, String comment) {
        this.memeId = meme.getId();
        this.memetickId = memetick.getId();
        this.comment = comment;

        this.creating = ZonedDateTime.now();
        this.point = 0;
    }
}
