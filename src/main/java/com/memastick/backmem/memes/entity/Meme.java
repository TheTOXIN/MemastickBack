package com.memastick.backmem.memes.entity;

import com.memastick.backmem.base.entity.AbstractEntity;
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

    @ManyToOne
    @JoinColumn(nullable = false)
    private Memetick memetick;

    @Column(nullable = false)
    private ZonedDateTime creating;

}
