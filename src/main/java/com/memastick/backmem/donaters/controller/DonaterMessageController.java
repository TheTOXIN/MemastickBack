package com.memastick.backmem.donaters.controller;

import com.memastick.backmem.donaters.entity.DonaterMessage;
import com.memastick.backmem.donaters.service.DonaterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("donater-messages")
@RequiredArgsConstructor
public class DonaterMessageController {

    private final DonaterService donaterService;

    @PostMapping("create")
    public void createMessage(@RequestBody DonaterMessage donater) {
        donaterService.createMessage(donater);
    }

    @GetMapping("random")
    public DonaterMessage readRandom() {
        return donaterService.readRandom();
    }
}
