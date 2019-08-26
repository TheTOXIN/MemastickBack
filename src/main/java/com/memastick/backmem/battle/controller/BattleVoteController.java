package com.memastick.backmem.battle.controller;

import com.memastick.backmem.battle.api.BattleResultAPI;
import com.memastick.backmem.battle.api.BattleVoteAPI;
import com.memastick.backmem.battle.service.BattleVoteService;
import com.memastick.backmem.main.api.IdAPI;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("battle-vote")
@AllArgsConstructor
public class BattleVoteController {

    private final BattleVoteService battleVoteService;

    @PatchMapping("give")
    public BattleResultAPI give(@RequestBody BattleVoteAPI api) {
        return battleVoteService.giveVote(api);
    }

    @GetMapping("list")
    public List<UUID> list() {
        return battleVoteService.battleList();
    }
}
