package com.memastick.backmem.battle.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("battle")
@AllArgsConstructor
public class BattleController {

    @GetMapping("home")
    public void home() {

    }

    @GetMapping("rating")
    public void rating() {

    }

    @GetMapping("view")
    public void view() {

    }
}
