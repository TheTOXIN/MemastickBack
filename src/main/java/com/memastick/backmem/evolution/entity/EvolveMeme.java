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

    @Column
    @Enumerated(EnumType.ORDINAL)
    private EvolveStep step;

    @Column(nullable = false)
    private long population;

    @Max(101)
    @Column(nullable = false)
    private Float chance = 0f;

    @Column(nullable = false)
    private boolean immunity = false;

    @Column(nullable = false)
    private int adaptation = 0;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "evolveMeme")
    private Meme meme;

    public EvolveMeme(long population) {
        this.step = EvolveStep.find(0);
        this.population = population;
    }
}
