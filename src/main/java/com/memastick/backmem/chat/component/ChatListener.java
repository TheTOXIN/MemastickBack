package com.memastick.backmem.chat.component;

import com.memastick.backmem.chat.constant.ChatMessageMode;
import com.memastick.backmem.chat.enitity.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.*;

@Component
@RequiredArgsConstructor
public class ChatListener {

    private final Map<String, UUID> online = new HashMap<>();

    private final SimpMessageSendingOperations sendingOperations;

    public List<UUID> online() {
        return new ArrayList<>(
            this.online.values()
        );
    }

    public void listen(ChatMessage message, String sessionId) {
        if (message.getMode().equals(ChatMessageMode.CONNECT)) {
            online.put(sessionId, message.getMemetickId());
        }

        if (message.getMode().equals(ChatMessageMode.DISCONNECT)) {
            online.remove(sessionId, message.getMemetickId());
        }
    }

    @EventListener(SessionDisconnectEvent.class)
    public void handleWebsocketDisconnectListener(SessionDisconnectEvent event) {
        if (online.containsKey(event.getSessionId())) {
            UUID memetickId = online.get(event.getSessionId());
            ChatMessage message = new ChatMessage();

            message.setMemetickId(memetickId);
            message.setMode(ChatMessageMode.DISCONNECT);

            sendingOperations.convertAndSend("/chat/main", message);
            online.remove(event.getSessionId());
        }
    }
}
