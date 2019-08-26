package com.memastick.backmem.battle.mapper;

import com.memastick.backmem.battle.entity.Battle;
import com.memastick.backmem.memetick.entity.Memetick;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Component
@AllArgsConstructor
public class BattleVoteMapper {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Battle battle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Memetick memetick;
}
