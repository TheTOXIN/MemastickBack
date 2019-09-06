package com.memastick.backmem.battle.controller;

import com.memastick.backmem.battle.api.BattleRequestAPI;
import com.memastick.backmem.battle.api.BattleResponseAPI;
import com.memastick.backmem.battle.service.BattleMemberService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("battle-member")
@AllArgsConstructor
public class BattleMemberController {

    private final BattleMemberService battleMemberService;

    @PutMapping("request")
    public void request(@RequestBody BattleRequestAPI api) {
        battleMemberService.request(api);
    }

    @PostMapping("response")
    public void response(@RequestBody BattleResponseAPI api) {
        battleMemberService.response(api);
    }
}
