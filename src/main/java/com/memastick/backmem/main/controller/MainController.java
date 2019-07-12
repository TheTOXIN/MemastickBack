package com.memastick.backmem.main.controller;

import com.memastick.backmem.evolution.service.EvolveNexterService;
import com.memastick.backmem.evolution.service.EvolveSelecterService;
import com.memastick.backmem.main.api.HomeAPI;
import com.memastick.backmem.main.api.NotifyCountAPI;
import com.memastick.backmem.main.constant.GlobalConstant;
import com.memastick.backmem.main.service.MainService;
import com.memastick.backmem.notification.service.NotifyService;
import com.memastick.backmem.tokens.service.TokenAllowanceSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    private final MainService mainService;
    private final EvolveNexterService evolveNexterService;
    private final TokenAllowanceSendService allowanceSendService;
    private final NotifyService notifyService;
    private final EvolveSelecterService evolveSelecterService;

    @Autowired
    public MainController(
        MainService mainService,
        EvolveNexterService evolveNexterService,
        TokenAllowanceSendService allowanceSendService,
        NotifyService notifyService,
        EvolveSelecterService evolveSelecterService
    ) {
        this.mainService = mainService;
        this.evolveNexterService = evolveNexterService;
        this.allowanceSendService = allowanceSendService;
        this.notifyService = notifyService;
        this.evolveSelecterService = evolveSelecterService;
    }

    @GetMapping("test")
    public void test() {

    }

    @GetMapping("hello")
    public String hello() {
        return "Hello, i'm MEMASTICK server!!! ver: " + GlobalConstant.VERSION;
    }

    @GetMapping("next-evolve")
    public void nextEvolve() {
        evolveNexterService.next();
    }

    @GetMapping("select-evolve")
    public void selectEvolve() {
        evolveSelecterService.select();
    }

    @GetMapping("send-allowance")
    public void allowance() {
        allowanceSendService.allowance();
    }

    @PatchMapping("admin-message")
    public void message(@RequestBody String message) {
        notifyService.sendADMIN(message);
    }

    @GetMapping("home")
    public HomeAPI home() {
        return mainService.home();
    }

    @GetMapping("notify-count")
    public NotifyCountAPI notifyCount() {
        return mainService.notifyCount();
    }
}
