package com.memastick.backmem.notification.controller;

import com.memastick.backmem.notification.service.NotificationService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notify")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(
        NotificationService notificationService
    ) {
        this.notificationService = notificationService;
    }

//    @MessageMapping("/register")
//    public void register(
//        @Payload String sessionId,
//        SimpMessageHeaderAccessor headerAccessor
//    ) {
//        notificationService.register(sessionId);
//    }

    @PutMapping("/register")
    public void register(@RequestBody String sessionId) {
        notificationService.register(sessionId);
    }
}
