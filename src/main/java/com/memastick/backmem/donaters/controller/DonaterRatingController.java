package com.memastick.backmem.donaters.controller;


import com.memastick.backmem.donaters.entity.DonaterRating;
import com.memastick.backmem.donaters.service.DonaterService;
import com.memastick.backmem.memotype.constant.MemotypeRarity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("donater-ratings")
@RequiredArgsConstructor
public class DonaterRatingController {

    private final DonaterService donaterService;

    @PostMapping("create")
    public void createRating(@RequestBody DonaterRating donater) {
        donaterService.createRating(donater);
    }

    @GetMapping("read")
    public Map<MemotypeRarity, List<DonaterRating>> readRating() {
        return donaterService.readRating();
    }
}
