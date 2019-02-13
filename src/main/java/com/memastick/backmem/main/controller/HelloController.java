package com.memastick.backmem.main.controller;

import com.memastick.backmem.evolution.service.EvolveNextService;
import com.memastick.backmem.main.constant.GlobalConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private EvolveNextService evolveNextService;

    @GetMapping("hello")
    public String hello() {
     evolveNextService.next();
        return "Hello, i'm MEMASTICK server!!! ver: " + GlobalConstant.VERSION;
    }

}
