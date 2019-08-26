package com.memastick.backmem.battle.dto;

import com.memastick.backmem.battle.constant.BattleRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BattleMemberViewDTO extends BattleMemberPreviewDTO {
    private UUID memetickId;
    private BattleRole role;
    private int votes;

    public BattleMemberViewDTO(UUID memberId, String memeUrl, UUID memetickId, BattleRole role, int votes) {
        super(memberId, memeUrl);
        this.memetickId = memetickId;
        this.role = role;
        this.votes = votes;
    }
}
