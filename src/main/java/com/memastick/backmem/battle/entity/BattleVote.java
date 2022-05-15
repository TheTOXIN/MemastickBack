package com.memastick.backmem.battle.entity;

import com.memastick.backmem.base.AbstractEntity;
import com.memastick.backmem.memetick.entity.Memetick;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "battle_vote")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BattleVote extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @ToString.Exclude
    private Battle battle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @ToString.Exclude
    private Memetick memetick;
}
