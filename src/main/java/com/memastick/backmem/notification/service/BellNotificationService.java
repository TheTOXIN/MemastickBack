package com.memastick.backmem.notification.service;

import com.memastick.backmem.notification.constant.NotificationType;
import com.memastick.backmem.notification.dto.BellNotificationDTO;
import com.memastick.backmem.security.service.SecurityService;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BellNotificationService {

    private Map<String, String> cache = new HashMap<>();

    private final SimpMessagingTemplate template;
    private final SecurityService securityService;

    public BellNotificationService(
        SimpMessagingTemplate template,
        SecurityService securityService
    ) {
        this.template = template;
        this.securityService = securityService;
    }

    public void register(String sessionId) {
        cache.put(securityService.getCurrentDetails().getUsername(), sessionId);
    }

    //TODO make async
    public void sendDna(int dna) {
        String username = securityService.getCurrentDetails().getUsername();
        String sessionId = cache.get(username);

        BellNotificationDTO dto = new BellNotificationDTO(
            NotificationType.DNK,
            Integer.toString(dna)
        );

        sendNotify(dto, sessionId);
    }

    public void sendAllowance() {
        cache.forEach((login, sessionId) ->
            sendNotify(
                new BellNotificationDTO(NotificationType.ALLOWANCE, null),
                sessionId
            )
        );
    }

    private void sendNotify(BellNotificationDTO dto, String sessionId) {
        if (sessionId == null) return;

        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);

        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);

        template.convertAndSendToUser(sessionId, "/queue/notify", dto, headerAccessor.getMessageHeaders());
    }
}
