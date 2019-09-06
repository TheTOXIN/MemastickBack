package com.memastick.backmem.battle.service;

import com.memastick.backmem.battle.api.BattleHomeAPI;
import com.memastick.backmem.battle.api.BattlePreviewAPI;
import com.memastick.backmem.battle.api.BattleViewAPI;
import com.memastick.backmem.battle.component.BattleMapper;
import com.memastick.backmem.battle.constant.BattleStatus;
import com.memastick.backmem.battle.entity.Battle;
import com.memastick.backmem.battle.repository.BattleRatingRepository;
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
    private final BattleRatingRepository battleRatingRepository;
    private final BattleMapper battleMapper;
    private final OauthData oauthData;

    public BattleHomeAPI home() {
        Memetick memetick = oauthData.getCurrentMemetick();
        List<Battle> battles = battleRepository.findAllByDefenderMemetickId(memetick.getId());

        Map<BattleStatus, List<BattleViewAPI>> mapBattles = battles
            .stream()
            .sorted(Comparator.comparing(Battle::getUpdating).reversed())
            .map(battle -> battleMapper.toView(battle, memetick))
            .collect(Collectors.groupingBy(BattleViewAPI::getStatus));

        long countMembers = battleRatingRepository.count();
        long countBattles = battleRepository.countByStatus(BattleStatus.START).orElse(0L);

        return new BattleHomeAPI(
            mapBattles,
            countBattles,
            countMembers
        );
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
