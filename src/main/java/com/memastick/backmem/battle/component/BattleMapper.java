package com.memastick.backmem.battle.component;

import com.memastick.backmem.battle.api.BattlePreviewAPI;
import com.memastick.backmem.battle.api.BattleViewAPI;
import com.memastick.backmem.battle.dto.BattleMemberPreviewDTO;
import com.memastick.backmem.battle.dto.BattleMemberViewDTO;
import com.memastick.backmem.battle.entity.Battle;
import com.memastick.backmem.battle.entity.BattleMember;
import com.memastick.backmem.memes.repository.MemeRepository;
import com.memastick.backmem.memetick.entity.Memetick;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BattleMapper {

    private final MemeRepository memeRepository;

    public BattleViewAPI toView(Battle battle, Memetick memetick) {
        return new BattleViewAPI(
            battle.getId(),
            battle.getStatus(),
            battle.getPvp(),
            toMemberView(battle.getForward()),
            toMemberView(battle.getDefender()),
            memetick.getId().equals(battle.getForward().getMemetickId())
        );
    }

    private BattleMemberViewDTO toMemberView(BattleMember member) {
        return new BattleMemberViewDTO(
            member.getRole(),
            member.getId(),
            member.getMemetickId(),
            member.getMemeId(),
            member.getVotes()
        );
    }

    public BattlePreviewAPI toPreview(Battle battle) {
        return new BattlePreviewAPI(
            battle.getId(),
            toMemberPreview(battle.getForward()),
            toMemberPreview(battle.getDefender())
        );
    }

    private BattleMemberPreviewDTO toMemberPreview(BattleMember member) {
        return new BattleMemberPreviewDTO(
            member.getId(),
            memeRepository.findUrlById(member.getMemeId())
        );
    }
}
