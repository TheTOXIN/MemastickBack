package com.memastick.backmem.battle.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("battle-vote")
@AllArgsConstructor
public class BattleVoteController {

    @PatchMapping("give")
    public void give() {

    }

    @GetMapping("list")
    public void list() {

    }
}
