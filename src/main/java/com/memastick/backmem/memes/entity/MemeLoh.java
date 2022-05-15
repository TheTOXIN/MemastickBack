package com.memastick.backmem.memes.entity;

import com.memastick.backmem.base.AbstractEntity;
import com.memastick.backmem.memetick.entity.Memetick;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import static com.memastick.backmem.main.constant.ValidConstant.MAX_LOH;
import static com.memastick.backmem.main.constant.ValidConstant.MIN_LOH;

@Entity
@Table(
    name = "memes_loh",
    uniqueConstraints = @UniqueConstraint(columnNames = {"meme_id", "memetick_id"})
)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemeLoh extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @ToString.Exclude
    private Meme meme;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @ToString.Exclude
    private Memetick memetick;

    @Min(MIN_LOH)
    @Max(MAX_LOH)
    @Column(nullable = false)
    private int lol = 0;

    @Min(MIN_LOH)
    @Max(MAX_LOH)
    @Column(nullable = false)
    private int omg = 0;

    @Min(MIN_LOH)
    @Max(MAX_LOH)
    @Column(nullable = false)
    private int hmm = 0;

    public MemeLoh(Meme meme, Memetick memetick) {
        this.meme = meme;
        this.memetick = memetick;
    }
}
