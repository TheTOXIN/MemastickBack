package com.memastick.backmem.chat.controller;

import com.memastick.backmem.chat.api.ChatConnectAPI;
import com.memastick.backmem.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("chat")
public class ChatController {

    private final ChatService chatService;

    @GetMapping("connect")
    public ChatConnectAPI connect() {
        return chatService.connect();
    }
}
