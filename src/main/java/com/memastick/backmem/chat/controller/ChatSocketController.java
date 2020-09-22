package com.memastick.backmem.chat.controller;

import com.memastick.backmem.chat.component.ChatListener;
import com.memastick.backmem.chat.enitity.ChatMessage;
import com.memastick.backmem.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatSocketController {

    private final ChatService chatService;
    private final ChatListener chatListener;

    @MessageMapping("/chat/send")
    @SendTo("/chat/main")
    public ChatMessage sendMessage(
        @Payload ChatMessage message,
        @Header("simpSessionId") String sessionId
    ) {
        chatService.save(message);
        chatListener.listen(message, sessionId);

        return message;
    }
}
