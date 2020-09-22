package com.memastick.backmem.notification.impl;

import com.memastick.backmem.main.component.SocketSessionStorage;
import com.memastick.backmem.notification.dto.NotifyDTO;
import com.memastick.backmem.notification.iface.NotifySender;
import com.memastick.backmem.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class NotifyWebService implements NotifySender {

    private final SocketSessionStorage socketSessionStorage;
    private final SimpMessagingTemplate template;

    @Override
    public void send(List<User> users, NotifyDTO dto) {
        users
            .stream()
            .filter(Objects::nonNull)
            .forEach(user -> sender(dto, user.getLogin()));
    }

    public void sendAll(NotifyDTO dto) {
        socketSessionStorage.allSessionId().forEach(name ->
            sender(dto, name)
        );
    }

    private void sender(Object data, String username) {
        String sessionId = socketSessionStorage.getSessionId(username);
        if (sessionId == null) return;

        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);

        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);

        template.convertAndSendToUser(sessionId, "/queue/notify", data, headerAccessor.getMessageHeaders());
    }
}
