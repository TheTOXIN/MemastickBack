package com.memastick.backmem.notification.controller;

import com.memastick.backmem.main.component.SocketSessionStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notify/web")
public class NotifyWebController {

    private final SocketSessionStorage socketSessionStorage;

    @Deprecated
    @PutMapping("/register")
    public void registerWeb(@RequestBody String sessionId) {
        socketSessionStorage.register(sessionId);
    }
}
