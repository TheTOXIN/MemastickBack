package com.memastick.backmem.notification.impl;

import com.memastick.backmem.notification.dto.NotifyDTO;
import com.memastick.backmem.notification.iface.NotifySender;
import com.memastick.backmem.security.component.OauthData;
import com.memastick.backmem.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class NotifyWebService implements NotifySender {

    private final static String PATH = "/queue/notify";
    private final static Map<String, String> CACHE = new HashMap<>();

    private final SimpMessagingTemplate template;
    private final OauthData oauthData;

    @Override
    public void send(List<User> users, NotifyDTO dto) {
        users
            .stream()
            .filter(Objects::nonNull)
            .forEach(user -> sender(dto, user.getLogin(), PATH));
    }

    public void sendAll(NotifyDTO dto) {
        CACHE.keySet().forEach(name ->
            sender(dto, name, PATH)
        );
    }

    public void sender(Object data, String username, String path) {
        String sessionId = CACHE.get(username);

        if (sessionId == null) return;

        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);

        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);

        template.convertAndSendToUser(sessionId, path, data, headerAccessor.getMessageHeaders());
    }

    public void register(String sessionId) {
        CACHE.put(oauthData.getCurrentDetails().getUsername(), sessionId);
    }

    public void remove() {
        CACHE.remove(oauthData.getCurrentDetails().getUsername());
    }
}
