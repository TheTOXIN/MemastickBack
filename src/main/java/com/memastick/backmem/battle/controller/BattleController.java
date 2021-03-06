package com.memastick.backmem.battle.controller;

import com.memastick.backmem.battle.api.BattleHomeAPI;
import com.memastick.backmem.battle.api.BattlePreviewAPI;
import com.memastick.backmem.battle.api.BattleViewAPI;
import com.memastick.backmem.battle.service.BattleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("battle")
@AllArgsConstructor
public class BattleController {

    private final BattleService battleService;

    @GetMapping("home")
    public BattleHomeAPI home() {
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
