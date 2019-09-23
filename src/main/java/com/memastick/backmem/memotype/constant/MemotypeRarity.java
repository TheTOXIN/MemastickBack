package com.memastick.backmem.memotype.constant;

import com.memastick.backmem.battle.constant.BattleConst;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
public enum MemotypeRarity {

    CLASSIC(1),
    RARE(2),
    EPIC(3),
    LEGENDARY(4),
    INCREDIBLE(5);

    @Getter
    private int lvl;

    public static MemotypeRarity findByPosition(int position) {
        return Arrays
            .stream(MemotypeRarity.values())
            .filter(e -> e.getLvl() == BattleConst.RATING_SIZE - position)
            .findFirst()
            .orElse(null);
    }
}
