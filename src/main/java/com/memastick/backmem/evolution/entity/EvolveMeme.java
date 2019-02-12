package com.memastick.backmem.evolution.entity;

import com.memastick.backmem.base.entity.AbstractEntity;
import com.memastick.backmem.evolution.constant.EvolveStep;
import com.memastick.backmem.memes.entity.Meme;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;

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

    @Column
    @Enumerated(EnumType.ORDINAL)
    private EvolveStep step;

    @Max(100)
    @Column(nullable = false)
    private long population;

    @Column
    private float chanceSurvive;

    public EvolveMeme(Meme meme, EvolveStep step, long population) {
        this.meme = meme;
        this.step = step;
        this.population = population;
    }
}
