package com.memastick.backmem.battle.api;

import com.memastick.backmem.battle.constant.BattleStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BattleHomeTMP {

    private Map<BattleStatus, List<BattleViewAPI>> battles;
    private int battlesCount = 0;
    private int membersCount = 0;
    //TODO add cookies count and status counter

    public BattleHomeTMP(Map<BattleStatus, List<BattleViewAPI>> battles) {
        this.battles = battles;
    }
}
