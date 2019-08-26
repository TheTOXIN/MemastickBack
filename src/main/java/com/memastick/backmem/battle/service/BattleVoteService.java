package com.memastick.backmem.battle.service;

import com.memastick.backmem.battle.api.BattleResultAPI;
import com.memastick.backmem.battle.api.BattleVoteAPI;
import com.memastick.backmem.battle.component.BattleChecker;
import com.memastick.backmem.battle.constant.BattleConst;
import com.memastick.backmem.battle.constant.BattleStatus;
import com.memastick.backmem.battle.entity.Battle;
import com.memastick.backmem.battle.entity.BattleMember;
import com.memastick.backmem.battle.entity.BattleVote;
import com.memastick.backmem.battle.repository.BattleMemberRepository;
import com.memastick.backmem.battle.repository.BattleRepository;
import com.memastick.backmem.battle.repository.BattleVoteRepository;
import com.memastick.backmem.errors.exception.BattleException;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.repository.MemetickRepository;
import com.memastick.backmem.memetick.service.MemetickService;
import com.memastick.backmem.security.component.OauthData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BattleVoteService {

    private final Map<UUID, Integer> comboCache = new HashMap<>();

    private final BattleVoteRepository battleVoteRepository;
    private final BattleRepository battleRepository;
    private final BattleMemberRepository battleMemberRepository;
    private final MemetickService memetickService;
    private final MemetickRepository memetickRepository;
    private final BattleChecker battleCheck;
    private final OauthData oauthData;

    public List<UUID> battleList() { // TODO filter self battles
        Memetick memetick = oauthData.getCurrentMemetick();

        List<UUID> battles = battleRepository.findAllBattleIds(BattleStatus.START);
        List<UUID> votes = battleVoteRepository.findAllBattleIds(memetick.getId());

        battles.removeAll(votes);

        return battles;
    }

    @Transactional
    public BattleResultAPI giveVote(BattleVoteAPI api) {
        Memetick memetick = oauthData.getCurrentMemetick();
        Battle battle = battleRepository.tryFindById(api.getBattleId());
        BattleMember member = battle.getMember(api.getMemberId());

        //TODO CHECK SELF VOTE
        //TODO check vote again

        if (!BattleStatus.START.equals(battle.getStatus())) throw new BattleException("BATTLE NOT START");
        if (!memetickService.haveCookie(memetick)) throw new BattleException("NOT HAVE COOKIE"); // TODO check cookie with cache
        if (member == null) throw new BattleException("MEMBER NOT FOUND");

        member.setVotes(member.getVotes() + 1);
        memetick.setCookies(memetick.getCookies() - 1);

        boolean guessed = member.equals(battle.getLeader());

        if (guessed) comboCache.merge(memetick.getId(), 1, Math::addExact); else comboCache.put(memetick.getId(), 1);
        int combo = comboCache.getOrDefault(memetick.getId(), 1);

        memetickService.addDna(memetick, combo * BattleConst.DNA_VOTE);

        battleVoteRepository.save(new BattleVote(battle, memetick));
        battleMemberRepository.save(member);

        battleCheck.check(battle);

        return new BattleResultAPI(
            battle.getForward().getVotes(),
            battle.getDefender().getVotes(),
            guessed,
            combo
        );
    }
}
