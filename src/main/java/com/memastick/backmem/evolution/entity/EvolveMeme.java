package com.memastick.backmem.evolution.entity;

import com.memastick.backmem.base.entity.AbstractEntity;
import com.memastick.backmem.evolution.constant.EvolveStep;
import com.memastick.backmem.memes.entity.Meme;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "evolve_memes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EvolveMeme extends AbstractEntity {

    @OneToOne
    @JoinColumn(nullable = false)
    private Meme meme;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private EvolveStep step;

    @Column(nullable = false)
    private long population;

    private int chanceSurvive;

    public EvolveMeme(Meme meme, EvolveStep step, long population) {
        this.meme = meme;
        this.step = step;
        this.population = population;
    }
}
