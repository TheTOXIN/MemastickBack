package com.memastick.backmem.notification.controller;

import com.memastick.backmem.notification.impl.NotifyWebService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notify/web")
public class NotifyWebController {

    private final NotifyWebService webService;

    @PutMapping("/register")
    public void registerWeb(@RequestBody String sessionId) {
        webService.register(sessionId);
    }
}
