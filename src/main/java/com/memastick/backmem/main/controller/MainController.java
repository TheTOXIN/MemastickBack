package com.memastick.backmem.main.controller;

import com.memastick.backmem.main.api.HomeAPI;
import com.memastick.backmem.main.api.InitAPI;
import com.memastick.backmem.main.constant.GlobalConstant;
import com.memastick.backmem.main.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;

    @GetMapping("hello")
    public String hello() {
        return "Hello, i'm MEMASTICK server!!! ver: " + GlobalConstant.VER;
    }

    @GetMapping("home")
    public HomeAPI home() {
        return mainService.home();
    }

    @GetMapping("init")
    public InitAPI init() {
        return mainService.init();
    }
}
