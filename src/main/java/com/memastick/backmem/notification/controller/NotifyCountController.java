package com.memastick.backmem.notification.controller;

import com.memastick.backmem.notification.api.NotifyCountAPI;
import com.memastick.backmem.notification.service.NotifyCountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NotifyCountController {

    private final NotifyCountService notifyCountService;

    @GetMapping("notify-count")
    public NotifyCountAPI notifyCount() {
        return notifyCountService.get();
    }
}
