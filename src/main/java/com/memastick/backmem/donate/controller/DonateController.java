package com.memastick.backmem.donate.controller;

import com.memastick.backmem.donate.api.DonateAPI;
import com.memastick.backmem.donate.service.DonateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("donate")
public class DonateController {

    private final DonateService donateService;

    @GetMapping("read")
    public DonateAPI read() {
        return donateService.read();
    }
}
