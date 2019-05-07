package com.memastick.backmem.notification.impl;

import com.memastick.backmem.notification.dto.NotifyDTO;
import com.memastick.backmem.notification.iface.NotifySender;
import com.memastick.backmem.security.service.SecurityService;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class NotifyWebService implements NotifySender {

    private Map<String, String> cache = new HashMap<>();

    private final SimpMessagingTemplate template;
    private final SecurityService securityService;

    public NotifyWebService(
        SimpMessagingTemplate template,
        SecurityService securityService
    ) {
        this.template = template;
        this.securityService = securityService;
    }

    @Override
    public void send(NotifyDTO dto) {
        dto.getUsers().forEach(user -> send(dto, cache.get(user.getLogin())));
    }

    private void send(NotifyDTO dto, String sessionId) {
        if (sessionId == null) return;

        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);

        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);

        template.convertAndSendToUser(sessionId, "/queue/notify", dto, headerAccessor.getMessageHeaders());
    }

    public void register(String sessionId) {
        cache.put(securityService.getCurrentDetails().getUsername(), sessionId);
    }
}
