package com.memastick.backmem.notification.controller;

import com.memastick.backmem.notification.impl.NotifyPushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notify/push")
public class NotifyPushController {

    private final NotifyPushService pushService;

    @Autowired
    public NotifyPushController(NotifyPushService pushService) {
        this.pushService = pushService;
    }

    @PostMapping("/register")
    public void register(@RequestBody String token) {
        pushService.register(token);
    }
}
