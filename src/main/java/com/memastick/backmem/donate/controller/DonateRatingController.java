package com.memastick.backmem.donate.controller;


import com.memastick.backmem.donate.entity.DonateRating;
import com.memastick.backmem.donate.service.DonateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("donate-ratings")
public class DonateRatingController {

    private final DonateService donateService;

    @PostMapping("create")
    public void createRating(@RequestBody DonateRating donate) {
        donateService.createRating(donate);
    }

    @GetMapping("read")
    public List<DonateRating> readRating() {
        return donateService.readRatings();
    }
}
