package com.memastick.backmem.memecoin.entity;

import com.memastick.backmem.base.entity.AbstractEntity;
import com.memastick.backmem.memetick.entity.Memetick;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "meme_coins")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MemeCoin extends AbstractEntity {

    @Column(nullable = false)
    private Long value;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Memetick memetick;

    @Column(nullable = false)
    private ZonedDateTime creating;
}
