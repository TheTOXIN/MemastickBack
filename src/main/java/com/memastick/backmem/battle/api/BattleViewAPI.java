package com.memastick.backmem.battle.api;

import com.memastick.backmem.battle.constant.BattleStatus;
import com.memastick.backmem.battle.dto.BattleMemberViewDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BattleViewAPI {

    private UUID battleId;
    private BattleStatus status;
    private Integer pvp;

    private BattleMemberViewDTO forward;
    private BattleMemberViewDTO defender;

    private boolean my;
}
