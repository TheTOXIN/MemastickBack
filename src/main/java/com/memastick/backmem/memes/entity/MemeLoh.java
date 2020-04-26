package com.memastick.backmem.memes.entity;

import com.memastick.backmem.base.AbstractEntity;
import com.memastick.backmem.main.constant.GlobalConstant;
import com.memastick.backmem.memes.dto.MemeLohDTO;
import com.memastick.backmem.memetick.entity.Memetick;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;

import static com.memastick.backmem.main.constant.GlobalConstant.MAX_LOH;

@Entity
@Table(
    name = "memes_loh",
    uniqueConstraints = @UniqueConstraint(columnNames = {"meme_id", "memetick_id"})
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MemeLoh extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Meme meme;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Memetick memetick;

    @Max(MAX_LOH)
    @Column(nullable = false)
    private int lol = 0;

    @Max(MAX_LOH)
    @Column(nullable = false)
    private int omg = 0;

    @Max(MAX_LOH)
    @Column(nullable = false)
    private int hmm = 0;

    public MemeLoh(Meme meme, Memetick memetick) {
        this.meme = meme;
        this.memetick = memetick;
    }
}
