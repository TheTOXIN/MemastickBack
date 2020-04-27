package com.memastick.backmem.memes.entity;

import com.memastick.backmem.base.AbstractEntity;
import com.memastick.backmem.memetick.entity.Memetick;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

import java.time.ZonedDateTime;

import static com.memastick.backmem.main.constant.ValidConstant.MAX_TEXT_LEN;

@Entity
@Table(
    name = "memes_comment",
    uniqueConstraints = @UniqueConstraint(columnNames = {"meme_id", "memetick_id"})
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MemeComment extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Meme meme;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Memetick memetick;

    @Column(nullable = false)
    @Length(max = MAX_TEXT_LEN)
    private String comment;

    @Column(nullable = false)
    private ZonedDateTime creating;

    @Column(nullable = false)
    private int point;

    public MemeComment(Meme meme, Memetick memetick, String comment) {
        this.meme = meme;
        this.memetick = memetick;
        this.comment = comment;

        this.creating = ZonedDateTime.now();
        this.point = 0;
    }
}
