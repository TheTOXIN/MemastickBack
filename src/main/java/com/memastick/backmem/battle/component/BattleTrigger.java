package com.memastick.backmem.battle.component;

import com.memastick.backmem.battle.constant.BattleConst;
import com.memastick.backmem.battle.entity.BattleRating;
import com.memastick.backmem.battle.repository.BattleRatingRepository;
import com.memastick.backmem.battle.repository.BattleVoteRepository;
import com.memastick.backmem.battle.service.BattleRatingService;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memotype.constant.MemotypeRarity;
import com.memastick.backmem.memotype.entity.Memotype;
import com.memastick.backmem.memotype.entity.MemotypeMemetick;
import com.memastick.backmem.memotype.repository.MemotypeMemetickRepository;
import com.memastick.backmem.memotype.repository.MemotypeRepository;
import com.memastick.backmem.notification.service.NotifyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@AllArgsConstructor
public class BattleTrigger {

    private final BattleRatingRepository battleRatingRepository;
    private final BattleRatingService battleRatingService;
    private final MemotypeRepository memotypeRepository;
    private final MemotypeMemetickRepository memotypeMemetickRepository;
    private final NotifyService notifyService;
    private final BattleVoteRepository battleVoteRepository;

    @Transactional
    public void ratingCheck() {
        Map<Integer, BattleRating> rating = battleRatingService.getRating();

        List<BattleRating> expire = battleRatingRepository.findAllExpire(
            LocalDate.now().minusDays(BattleConst.RATING_DAY)
        );

        rating.entrySet().stream().filter(e -> expire.contains(e.getValue())).forEach(r -> {
            MemotypeRarity rarity = MemotypeRarity.findByPosition(r.getKey());
            if (rarity == null) return;

            Optional<Memotype> optional = memotypeRepository.randomMemotypeByRarity(rarity.name());
            if (optional.isEmpty()) return;

            Memotype memotype = optional.get();
            Memetick memetick = r.getValue().getMemetick();

            memotypeMemetickRepository.save(new MemotypeMemetick(memetick, memotype));
            notifyService.sendBATTLERATING(memetick, memotype, r.getKey());
        });

        battleRatingRepository.deleteAll(expire);
        battleVoteRepository.deleteAll();
    }
}
