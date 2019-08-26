package com.memastick.backmem.battle.controller;

import com.memastick.backmem.battle.api.BattleHomeAPI;
import com.memastick.backmem.battle.api.BattleViewAPI;
import com.memastick.backmem.battle.service.BattleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("battle")
@AllArgsConstructor
public class BattleController {

    private final BattleService battleService;

    @GetMapping("home")
    public BattleHomeAPI home() {
        return battleService.home();
    }

    @GetMapping("view")
    public BattleViewAPI view() {
        return battleService.view();
    }
}
