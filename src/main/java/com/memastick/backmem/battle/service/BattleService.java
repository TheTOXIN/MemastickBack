package com.memastick.backmem.battle.service;

import com.memastick.backmem.battle.api.BattleHomeAPI;
import com.memastick.backmem.battle.api.BattlePreviewAPI;
import com.memastick.backmem.battle.api.BattleViewAPI;
import com.memastick.backmem.battle.component.BattleMapper;
import com.memastick.backmem.battle.constant.BattleStatus;
import com.memastick.backmem.battle.entity.Battle;
import com.memastick.backmem.battle.repository.BattleRepository;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.security.component.OauthData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BattleService {

    private final BattleRepository battleRepository;
    private final BattleMapper battleMapper;
    private final OauthData oauthData;

    public BattleHomeAPI home() {
        Memetick memetick = oauthData.getCurrentMemetick();
        List<Battle> battles = battleRepository.findAllByMemetickId(memetick.getId());

        return new BattleHomeAPI(battles
            .stream()
            .sorted(Comparator.comparing(Battle::getUpdating))
            .map(b -> battleMapper.toView(b, memetick))
            .collect(Collectors.groupingBy(BattleViewAPI::getStatus)));
    }

    public BattleViewAPI view(UUID battleId) {
        Memetick memetick = oauthData.getCurrentMemetick();
        return battleMapper.toView(
            battleRepository.tryFindByMemetickAndId(
                memetick,
                battleId
            ), memetick
        );
    }

    public BattlePreviewAPI preview(UUID battleId) {
        return battleMapper.toPreview(
            battleRepository.tryFindById(battleId)
        );
    }

    public void battleUpdate(BattleStatus status, Battle battle) {
        battle.setStatus(status);
        battle.setUpdating(ZonedDateTime.now());
        battleRepository.save(battle);
    }
}
