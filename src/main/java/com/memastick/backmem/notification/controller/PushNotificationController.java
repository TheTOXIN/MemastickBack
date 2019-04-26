package com.memastick.backmem.notification.controller;

import com.memastick.backmem.notification.service.PushTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/push-notify")
public class PushNotificationController {

    private final PushTokenService pushTokenService;

    @Autowired
    public PushNotificationController(PushTokenService pushTokenService) {
        this.pushTokenService = pushTokenService;
    }

    @PostMapping("/update")
    public void update(@RequestBody String token) {
        pushTokenService.update(token);
    }
}
