package com.memastick.backmem.donate.controller;

import com.memastick.backmem.donate.entity.DonateMessage;
import com.memastick.backmem.donate.service.DonateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("donate-messages")
public class DonateMessageController {

    private final DonateService donateService;

    @PostMapping("create")
    public void createMessage(@RequestBody DonateMessage donate) {
        donateService.createMessage(donate);
    }

    @GetMapping("random")
    public DonateMessage readRandom() {
        return donateService.readRandom();
    }

    @GetMapping("read")
    public List<DonateMessage> read() {
        return donateService.readMessages();
    }
}
