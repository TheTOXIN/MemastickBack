package com.memastick.backmem.battle.api;

import com.memastick.backmem.battle.constant.BattleStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BattleHomeAPI {

    private Map<BattleStatus, List<BattleViewAPI>> battles = new HashMap<>();

    private long battlesCount;
    private long membersCount;
}
