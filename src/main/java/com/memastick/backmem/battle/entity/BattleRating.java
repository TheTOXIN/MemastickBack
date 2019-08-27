package com.memastick.backmem.battle.entity;

import com.memastick.backmem.base.AbstractEntity;
import com.memastick.backmem.memetick.entity.Memetick;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "battle_rating")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BattleRating extends AbstractEntity {

    @OneToOne
    @JoinColumn(nullable = false)
    private Memetick memetick;

    @Column(nullable = false)
    private LocalDate localDate = LocalDate.now();

    @Column(nullable = false)
    private int score = 0;

    public BattleRating(Memetick memetick) {
        this.memetick = memetick;
    }
}
