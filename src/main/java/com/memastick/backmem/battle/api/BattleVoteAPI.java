package com.memastick.backmem.battle.api;

import com.memastick.backmem.battle.constant.BattleRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BattleVoteAPI {

    private UUID battleId;
    private UUID memberId;
}
