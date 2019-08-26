package com.memastick.backmem.battle.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("battle-member")
@AllArgsConstructor
public class BattleMemberController {

    @PutMapping("request")
    public void request() {

    }

    @PostMapping("response")
    public void response() {

    }
}
