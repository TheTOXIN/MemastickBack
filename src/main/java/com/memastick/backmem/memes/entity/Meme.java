package com.memastick.backmem.memes.entity;

import com.memastick.backmem.base.entity.AbstractEntity;
import com.memastick.backmem.main.dto.EPI;
import com.memastick.backmem.memes.constant.MemeType;
import com.memastick.backmem.memetick.entity.Memetick;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "memes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Meme extends AbstractEntity {

    @Column(nullable = false, unique = true)
    private UUID fireId;

    @Column(length = 512, nullable = false, unique = true)
    private String url;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private Memetick memetick;

    @Column(nullable = false)
    private ZonedDateTime creating;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private MemeType type;

    @Column(nullable = false)
    private int chromosomes = 0;

    // -=[EPI]=-

    @Column(nullable = false)
    private long evolution;

    @Column(nullable = false)
    private long population;

    @Column(nullable = false)
    private long individuation;

    public Meme(UUID fireId, String url, Memetick memetick, EPI epi) {
        this.fireId = fireId;
        this.url = url;
        this.memetick = memetick;

        this.creating = ZonedDateTime.now();
        this.type = MemeType.EVLV;

        this.evolution = epi.getEvolution();
        this.population = epi.getPopulation();
        this.individuation = epi.getIndividuation();
    }
}
