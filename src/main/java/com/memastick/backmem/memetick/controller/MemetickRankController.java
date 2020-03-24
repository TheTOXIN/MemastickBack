package com.memastick.backmem.memetick.controller;

import com.memastick.backmem.memetick.api.RankTokenAPI;
import com.memastick.backmem.memetick.api.RankTypeAPI;
import com.memastick.backmem.memetick.service.MemetickRankInfoService;
import com.memastick.backmem.memetick.service.MemetickRankService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("memetick-rank")
@RequiredArgsConstructor
public class MemetickRankController {

    private final MemetickRankInfoService rankInfoService;

    @GetMapping("types")
    public List<RankTypeAPI> rankTypes() {
        return rankInfoService.getRankTypes();
    }

    @GetMapping("tokens")
    public List<RankTokenAPI> rankTokens() {
        return rankInfoService.getRankTokens();
    }
}
