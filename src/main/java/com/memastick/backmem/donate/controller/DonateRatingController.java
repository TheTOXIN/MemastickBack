package com.memastick.backmem.donate.controller;


import com.memastick.backmem.donate.entity.DonateRating;
import com.memastick.backmem.donate.service.DonateService;
import com.memastick.backmem.memotype.constant.MemotypeRarity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("donate-ratings")
@RequiredArgsConstructor
public class DonateRatingController {

    private final DonateService donateService;

    @PostMapping("create")
    public void createRating(@RequestBody DonateRating donate) {
        donateService.createRating(donate);
    }

    @GetMapping("read")
    public Map<MemotypeRarity, List<DonateRating>> readRating() {
        return donateService.readRating();
    }
}
