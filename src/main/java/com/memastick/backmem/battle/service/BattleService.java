package com.memastick.backmem.battle.service;

import com.memastick.backmem.battle.constant.BattleStatus;
import com.memastick.backmem.battle.entity.Battle;
import com.memastick.backmem.battle.repository.BattleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
@AllArgsConstructor
public class BattleService {

    private final BattleRepository battleRepository;

    public void battleUpdate(BattleStatus status, Battle battle) {
        battle.setStatus(status);
        battle.setUpdating(ZonedDateTime.now());

        battleRepository.save(battle);
    }
}
