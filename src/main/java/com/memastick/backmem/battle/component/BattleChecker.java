package com.memastick.backmem.battle.component;

import com.memastick.backmem.battle.constant.BattleConst;
import com.memastick.backmem.battle.constant.BattleStatus;
import com.memastick.backmem.battle.entity.Battle;
import com.memastick.backmem.battle.entity.BattleMember;
import com.memastick.backmem.battle.service.BattleRatingService;
import com.memastick.backmem.battle.service.BattleService;
import com.memastick.backmem.main.util.MathUtil;
import com.memastick.backmem.memecoin.service.MemeCoinService;
import com.memastick.backmem.memes.constant.MemeType;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memes.repository.MemeRepository;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.repository.MemetickRepository;
import com.memastick.backmem.notification.service.NotifyService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class BattleChecker {

    private final MemeCoinService coinService;
    private final MemetickRepository memetickRepository;
    private final MemeRepository memeRepository;
    private final NotifyService notifyService;
    private final BattleService battleService;
    private final BattleRatingService battleRatingService;

    @Async
    @Transactional
    public void check(Battle battle) {
        if (!BattleStatus.START.equals(battle.getStatus())) return;

        BattleMember forward = battle.getForward();
        BattleMember defender = battle.getDefender();

        int absents = MathUtil.absents(forward.getVotes(), defender.getVotes());
        boolean complete = absents >= battle.getPvp();

        if (!complete) return;

        processMember(battle, battle.getLeader(), true);
        processMember(battle, battle.getLooser(), false);

        battleService.battleUpdate(BattleStatus.END, battle);
    }

    private void processMember(Battle battle, BattleMember member, boolean isWin) {
        if (member == null) return;

        Memetick memetick = memetickRepository.tryFindById(member.getMemetickId());
        Meme meme = memeRepository.tryFindById(member.getMemeId());

        if (isWin) processLeader(memetick, battle.getPvp());
        else processLooser(meme);

        Integer pvpRating = isWin ? battle.getPvp() : battle.getPvp() * -1;

        battleRatingService.generate(memetick, pvpRating);
        notifyService.sendBATTLECOMPETE(battle, memetick, isWin);
    }

    private void processLeader(Memetick memetick, int pvp) {
        int coins = pvp * BattleConst.MEMCOIN_PRESENT;
        coinService.transaction(memetick, coins);
    }

    private void processLooser(Meme meme) {
        meme.setType(MemeType.DEAD);
        memeRepository.save(meme);
    }
}
