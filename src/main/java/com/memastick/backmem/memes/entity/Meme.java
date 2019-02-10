package com.memastick.backmem.memes.entity;

import com.memastick.backmem.base.entity.AbstractEntity;
import com.memastick.backmem.evolution.entity.EvolveMeme;
import com.memastick.backmem.memes.constant.MemeType;
import com.memastick.backmem.person.entity.Memetick;
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

    @ManyToOne
    @JoinColumn(nullable = false)
    private Memetick memetick;

    @Column(nullable = false)
    private ZonedDateTime creating;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemeType type;

}
