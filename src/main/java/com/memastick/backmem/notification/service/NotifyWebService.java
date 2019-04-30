package com.memastick.backmem.notification.service;

import com.memastick.backmem.notification.dto.NotifyWebDTO;
import com.memastick.backmem.security.service.SecurityService;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class NotifyWebService {

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

    public void register(String sessionId) {
        cache.put(securityService.getCurrentDetails().getUsername(), sessionId);
    }

    public void send(NotifyWebDTO dto) {
        String username = securityService.getCurrentDetails().getUsername();
        String sessionId = cache.get(username);

        sendNotify(dto, sessionId);
    }

    private void sendNotify(NotifyWebDTO dto, String sessionId) {
        if (sessionId == null) return;

        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);

        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);

        template.convertAndSendToUser(sessionId, "/queue/notify", dto, headerAccessor.getMessageHeaders());
    }
}
