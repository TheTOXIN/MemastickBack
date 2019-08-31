package com.memastick.backmem.battle.controller;

import com.memastick.backmem.battle.api.BattleHomeTMP;
import com.memastick.backmem.battle.api.BattlePreviewAPI;
import com.memastick.backmem.battle.api.BattleViewAPI;
import com.memastick.backmem.battle.service.BattleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("battle")
@AllArgsConstructor
public class BattleController {

    private final BattleService battleService;

    @GetMapping("home")
    public BattleHomeTMP home() {
        return battleService.home();
    }

    @GetMapping("view/{battleId}")
    public BattleViewAPI view(@PathVariable("battleId") UUID battleId) {
        return battleService.view(battleId);
    }

    @GetMapping("preview/{battleId}")
    public BattlePreviewAPI read(@PathVariable("battleId") UUID battleId) {
        return battleService.preview(battleId);
    }
}
