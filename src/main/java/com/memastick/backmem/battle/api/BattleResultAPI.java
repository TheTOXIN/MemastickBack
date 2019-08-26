package com.memastick.backmem.battle.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BattleResultAPI {

    private int forwardCount;
    private int defenderCount;
    private boolean iamGuessed;
    private int dnaCombo;
}
