package com.memastick.backmem.memes.entity;

import com.memastick.backmem.base.AbstractEntity;
import com.memastick.backmem.main.dto.EPI;
import com.memastick.backmem.memes.api.MemeCreateAPI;
import com.memastick.backmem.memes.constant.MemeType;
import com.memastick.backmem.memetick.entity.Memetick;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.UUID;

import static com.memastick.backmem.main.constant.ValidConstant.MAX_TEXT_LEN;

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

    @Column
    @Length(max = MAX_TEXT_LEN)
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private MemeType type;

    @Column(nullable = false)
    private ZonedDateTime creating;

    @Column(nullable = false)
    private int likes = 0;

    @Column(nullable = false)
    private int chromosomes = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Memetick memetick;

    // -=[EPI]=-

    @Column(nullable = false)
    private long evolution;

    @Column(nullable = false)
    private long population;

    @Column(nullable = false)
    private long individuation;

    public Meme(MemeCreateAPI api, Memetick memetick, EPI epi) {
        this.fireId = api.getFireId();
        this.url = api.getUrl();
        this.text = api.getText();

        this.memetick = memetick;
        this.creating = ZonedDateTime.now();
        this.type = MemeType.EVLV;

        this.evolution = epi.getEvolution();
        this.population = epi.getPopulation();
        this.individuation = epi.getIndividuation();
    }
}
