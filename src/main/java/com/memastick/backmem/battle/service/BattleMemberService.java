package com.memastick.backmem.battle.service;

import com.memastick.backmem.battle.api.BattleRequestAPI;
import com.memastick.backmem.battle.api.BattleResponseAPI;
import com.memastick.backmem.battle.constant.BattleConst;
import com.memastick.backmem.battle.constant.BattleRole;
import com.memastick.backmem.battle.constant.BattleStatus;
import com.memastick.backmem.battle.entity.Battle;
import com.memastick.backmem.battle.entity.BattleMember;
import com.memastick.backmem.battle.repository.BattleRepository;
import com.memastick.backmem.errors.consts.ErrorCode;
import com.memastick.backmem.errors.exception.BattleException;
import com.memastick.backmem.main.util.ValidationUtil;
import com.memastick.backmem.memes.constant.MemeType;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memes.repository.MemeRepository;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.repository.MemetickRepository;
import com.memastick.backmem.memetick.service.MemetickService;
import com.memastick.backmem.notification.service.NotifyService;
import com.memastick.backmem.security.component.OauthData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class BattleMemberService {

    private final BattleService battleService;
    private final BattleRepository battleRepository;
    private final MemeRepository memeRepository;
    private final NotifyService notifyService;
    private final MemetickRepository memetickRepository;
    private final OauthData oauthData;

    @Transactional
    public void request(BattleRequestAPI api) {
        Memetick currentMemetick = oauthData.getCurrentMemetick();

        Integer cookies = memetickRepository.findCookieByMemetickId(currentMemetick.getId()).orElse(0);
        if (cookies < BattleConst.MAX_PVP) throw new BattleException(ErrorCode.BATTLE_COOKIE);

        currentMemetick.setCookies(cookies - BattleConst.MAX_PVP);
        memetickRepository.save(currentMemetick);

        Meme fromMeme = memeRepository.tryFindById(api.getFromMeme());
        Meme toMeme = memeRepository.tryFindById(api.getToMeme());

        if (!MemeType.INDV.equals(fromMeme.getType())) throw new BattleException("FROM NOT INVD");
        if (!MemeType.INDV.equals(toMeme.getType())) throw new BattleException("TO NOT INVD");

        Memetick forwardMemetick = fromMeme.getMemetick();
        Memetick defenderMemetick = toMeme.getMemetick();

        if (!forwardMemetick.getId().equals(currentMemetick.getId())) throw new BattleException("NOT MY FROM");
        if (defenderMemetick.getId().equals(currentMemetick.getId())) throw new BattleException(ErrorCode.BATTLE_REQUEST_ME);

        Battle battle = new Battle(
            new BattleMember(fromMeme, BattleRole.FORWARD),
            new BattleMember(toMeme, BattleRole.DEFENDER)
        );

        battleService.battleUpdate(BattleStatus.WAIT, battle);
        notifyService.sendBATTLEREQUEST(battle, forwardMemetick.getNick(), defenderMemetick);
    }

    @Transactional
    public void response(BattleResponseAPI api) {
        Battle battle = battleRepository.tryFindById(api.getBattleId());
        Memetick memetick = oauthData.getCurrentMemetick();

        if (!BattleStatus.WAIT.equals(battle.getStatus())) throw new BattleException("BATTLE NOT WAIT");
        if (!battle.getDefender().getMemetickId().equals(memetick.getId())) throw new BattleException("NOT ME TO");
        if (!ValidationUtil.checkPVP(api.getPvp())) throw new BattleException("PVP INVALID");

        BattleStatus status = api.isAccept() ? BattleStatus.START : BattleStatus.CANCEL;

        if (BattleStatus.START.equals(status)) battle.setPvp(api.getPvp());

        battleService.battleUpdate(status, battle);
        notifyService.sendBATTLERESPONSE(battle, battle.getForward().getMemetickId(), memetick.getNick(), api.isAccept());
    }
}
