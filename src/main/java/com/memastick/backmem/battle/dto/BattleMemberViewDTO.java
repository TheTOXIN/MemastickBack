package com.memastick.backmem.battle.dto;

import com.memastick.backmem.battle.constant.BattleRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BattleMemberViewDTO  {

    private BattleRole role;

    private UUID memberId;
    private UUID memetickId;
    private UUID memeId;

    private int votes;
}
