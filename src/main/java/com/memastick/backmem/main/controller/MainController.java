package com.memastick.backmem.main.controller;

import com.memastick.backmem.main.api.HomeAPI;
import com.memastick.backmem.main.constant.GlobalConstant;
import com.memastick.backmem.main.service.MainService;
import com.memastick.backmem.tokens.service.TokenAllowanceSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    private final MainService mainService;

    @Autowired
    private TokenAllowanceSendService tokenAllowanceSendService;

    @Autowired
    public MainController(
        MainService mainService
    ) {
        this.mainService = mainService;
    }

    @GetMapping("hello")
    public String hello() {
        tokenAllowanceSendService.allowance();
        return "Hello, i'm MEMASTICK server!!! ver: " + GlobalConstant.VERSION;
    }

    @GetMapping("home")
    public HomeAPI home() {
        return mainService.home();
    }
}
