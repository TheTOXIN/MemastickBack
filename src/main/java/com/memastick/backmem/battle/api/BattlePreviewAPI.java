package com.memastick.backmem.battle.api;

import com.memastick.backmem.battle.dto.BattleMemberPreviewDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BattlePreviewAPI {

    private UUID battleId;

    private BattleMemberPreviewDTO forward;
    private BattleMemberPreviewDTO defender;
}