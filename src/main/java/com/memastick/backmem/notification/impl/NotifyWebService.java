package com.memastick.backmem.notification.impl;

import com.memastick.backmem.notification.dto.NotifyDTO;
import com.memastick.backmem.notification.iface.NotifySender;
import com.memastick.backmem.security.component.OauthData;
import com.memastick.backmem.user.entity.User;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NotifyWebService implements NotifySender {

    private Map<String, String> cache = new HashMap<>();

    private final SimpMessagingTemplate template;
    private final OauthData oauthData;

    public NotifyWebService(
        SimpMessagingTemplate template,
        OauthData oauthData
    ) {
        this.template = template;
        this.oauthData = oauthData;
    }

    @Override
    public void send(List<User> users, NotifyDTO dto) {
        users.forEach(user -> send(dto, cache.get(user.getLogin())));
    }

    private void send(NotifyDTO dto, String sessionId) {
        if (sessionId == null) return;

        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);

        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);

        template.convertAndSendToUser(sessionId, "/queue/notify", dto, headerAccessor.getMessageHeaders());
    }

    public void register(String sessionId) {
        cache.put(oauthData.getCurrentDetails().getUsername(), sessionId);
    }

    public void remove() {
        cache.remove(oauthData.getCurrentDetails().getUsername());
    }
}
