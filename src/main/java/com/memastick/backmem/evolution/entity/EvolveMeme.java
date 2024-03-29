package com.memastick.backmem.evolution.entity;

import com.memastick.backmem.base.AbstractEntity;
import com.memastick.backmem.evolution.constant.EvolveStep;
import com.memastick.backmem.memes.entity.Meme;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;

@Entity
@Table(name = "evolve_memes")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraph(name = "joinedMeme", includeAllAttributes = true)
public class EvolveMeme extends AbstractEntity {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, unique = true)
    private Meme meme;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private EvolveStep step;

    @Column(nullable = false)
    private boolean immunity = false;

    @Column(nullable = false)
    private int adaptation = 0;

    // TODO Hmm...
    @Column
    @Max(101)
    private float chance;

    public EvolveMeme(Meme meme) {
        this.meme = meme;
        this.step = EvolveStep.find(1);
    }
}
