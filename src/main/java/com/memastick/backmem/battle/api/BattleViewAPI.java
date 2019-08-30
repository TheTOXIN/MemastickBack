package com.memastick.backmem.battle.api;

import com.memastick.backmem.battle.constant.BattleStatus;
import com.memastick.backmem.battle.dto.BattleMemberViewDTO;
import lombok.*;

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
