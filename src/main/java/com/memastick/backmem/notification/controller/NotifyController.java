package com.memastick.backmem.notification.controller;

import com.memastick.backmem.notification.service.NotifyPushService;
import com.memastick.backmem.notification.service.NotifyWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notify")
public class NotifyController {

    private final NotifyPushService notifyPushService;
    private final NotifyWebService notifyWebService;

    @Autowired
    public NotifyController(
        NotifyPushService notifyPushService,
        NotifyWebService notifyWebService
    ) {
        this.notifyPushService = notifyPushService;
        this.notifyWebService = notifyWebService;
    }

    @PostMapping("/push/register")
    public void registerPush(@RequestBody String token) {
        notifyPushService.register(token);
    }

    @PutMapping("/web/register")
    public void registerWeb(@RequestBody String sessionId) {
        notifyWebService.register(sessionId);
    }
}
