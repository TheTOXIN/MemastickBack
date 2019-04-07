package com.memastick.backmem.notification.service;

import com.memastick.backmem.notification.NotificationType;
import com.memastick.backmem.notification.dto.NotificationDTO;
import com.memastick.backmem.security.service.SecurityService;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class NotificationService {

    private Map<String, String> cache = new HashMap<>();

    private final SimpMessagingTemplate template;
    private final SecurityService securityService;

    public NotificationService(
        SimpMessagingTemplate template,
        SecurityService securityService
    ) {
        this.template = template;
        this.securityService = securityService;
    }

    public void register(String sessionId) {
        cache.put(securityService.getCurrentUser().getUsername(), sessionId);
    }

    //TODO make async
    public void sendNotifyDNA(int dna) {
        String username = securityService.getCurrentUser().getUsername();
        String sessionId = cache.get(username);

        NotificationDTO dto = new NotificationDTO(
            NotificationType.DNK,
            Integer.toString(dna)
        );

        sendNotify(dto, sessionId);
    }

    private void sendNotify(NotificationDTO dto, String sessionId) {
        if (sessionId == null) return;

        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);

        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);

        template.convertAndSendToUser(sessionId, "/queue/notify", dto, headerAccessor.getMessageHeaders());
    }
}
