package com.memastick.backmem.chat.controller;

import com.memastick.backmem.chat.enitity.ChatMessage;
import com.memastick.backmem.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("/chat/send")
    @SendTo("/chat/main")
    public ChatMessage sendMessage(@Payload ChatMessage message) {
        chatService.save(message);
        return message;
    }
}
