package com.memastick.backmem.chat.controller;

import com.memastick.backmem.chat.enitity.ChatMessage;
import com.memastick.backmem.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("chat-messages")
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatService chatService;

    @GetMapping
    public List<ChatMessage> read(Pageable pageable) {
        return chatService.read(pageable);
    }

    @PostMapping
    public void save(@RequestBody ChatMessage message) {
        chatService.save(message);
    }

    @DeleteMapping("/{number}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void delete(@PathVariable("number") Long number) {
        chatService.delete(number);
    }
}
