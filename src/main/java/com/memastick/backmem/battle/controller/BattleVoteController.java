package com.memastick.backmem.battle.controller;

import com.memastick.backmem.battle.api.BattleResultAPI;
import com.memastick.backmem.battle.api.BattleVoteAPI;
import com.memastick.backmem.battle.service.BattleVoteService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("battle-vote")
@AllArgsConstructor
public class BattleVoteController {

    private final BattleVoteService battleVoteService;

    @PatchMapping("give")
    public BattleResultAPI give(BattleVoteAPI api) {
        return battleVoteService.giveVote(api);
    }

    @GetMapping("list")
    public List<UUID> list() {
        return battleVoteService.battleList();
    }
}
