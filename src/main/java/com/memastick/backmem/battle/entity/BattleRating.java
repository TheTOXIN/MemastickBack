package com.memastick.backmem.battle.entity;

import com.memastick.backmem.base.AbstractEntity;
import com.memastick.backmem.memes.entity.Meme;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "battle_rating")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BattleRating extends AbstractEntity {

    @OneToOne
    @JoinColumn(nullable = false, unique = true)
    private Meme meme;

    @Column(nullable = false)
    private int score = 0;
}
