package com.memastick.backmem.memes.entity;

import com.memastick.backmem.base.entity.AbstractEntity;
import com.memastick.backmem.evolution.entity.EvolveMeme;
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private Memetick memetick;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, unique = true)
    private EvolveMeme evolveMeme;

    @Column(nullable = false, unique = true)
    private UUID fireId;

    @Column(length = 512, nullable = false, unique = true)
    private String url;

    @Column(nullable = false)
    private ZonedDateTime creating = ZonedDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private MemeType type = MemeType.EVOLVE;

    @Column(nullable = false)
    private int chromosomes = 0;

    public Meme(
        Memetick memetick,
        UUID fireId,
        String url
    ) {
        this.memetick = memetick;
        this.fireId = fireId;
        this.url = url;
    }
}
