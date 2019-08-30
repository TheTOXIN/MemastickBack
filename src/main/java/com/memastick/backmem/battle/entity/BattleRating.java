package com.memastick.backmem.battle.entity;

import com.memastick.backmem.base.AbstractEntity;
import com.memastick.backmem.battle.constant.BattleConst;
import com.memastick.backmem.memetick.entity.Memetick;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.joda.time.Days;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "battle_rating")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@NamedEntityGraph(name = "joinedRatingMemetick", includeAllAttributes = true)
public class BattleRating extends AbstractEntity {

    @OneToOne
    @JoinColumn(nullable = false)
    private Memetick memetick;

    @Column(nullable = false)
    private LocalDate date = LocalDate.now();

    @Column(nullable = false)
    private int score = 0;

    public BattleRating(Memetick memetick) {
        this.memetick = memetick;
    }

    public int leftDays() {
        LocalDate expire = getDate().plusDays(BattleConst.RATING_DAY);
        long between = ChronoUnit.DAYS.between(LocalDate.now(), expire);

        return Math.max(0, (int) between);
    }
}
