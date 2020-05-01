package com.memastick.backmem.notification.controller;

import com.memastick.backmem.notification.impl.NotifyPushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/refresher/{token}")
    public void refresher(
        @PathVariable("token") String oldToken,
        @RequestBody String newToken
    ) {
        pushService.refresher(oldToken, newToken);
    }
}
