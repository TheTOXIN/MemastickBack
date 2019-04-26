package com.memastick.backmem.notification.controller;

import com.memastick.backmem.notification.service.BellNotificationService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bell-notify")
public class BellNotificationController {

    private final BellNotificationService bellNotificationService;

    public BellNotificationController(
        BellNotificationService bellNotificationService
    ) {
        this.bellNotificationService = bellNotificationService;
    }

    @PutMapping("/register")
    public void register(@RequestBody String sessionId) {
        bellNotificationService.register(sessionId);
    }
}
