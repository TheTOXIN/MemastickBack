package com.memastick.backmem.battle.controller;


import com.memastick.backmem.battle.api.BattleRatingAPI;
import com.memastick.backmem.battle.service.BattleRatingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("battle-rating")
@AllArgsConstructor
public class BattleRatingController {

    private final BattleRatingService battleRatingService;

    @GetMapping("main")
    public List<BattleRatingAPI> mainRating() {
        return battleRatingService.mainRating();
    }

    @GetMapping("my")
    public BattleRatingAPI myRating() {
        return battleRatingService.myRating();
    }
}
