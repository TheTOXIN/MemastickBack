package com.memastick.backmem.notification.service;

import com.memastick.backmem.memetick.service.MemetickInventoryService;
import com.memastick.backmem.notification.api.NotifyCountAPI;
import com.memastick.backmem.notification.constant.NotifyCountAction;
import com.memastick.backmem.notification.constant.NotifyCountType;
import com.memastick.backmem.notification.dto.NotifyCountDTO;
import com.memastick.backmem.notification.impl.NotifyBellService;
import com.memastick.backmem.notification.impl.NotifyWebService;
import com.memastick.backmem.security.component.OauthData;
import com.memastick.backmem.user.entity.User;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotifyCountService {

    private final NotifyWebService webService;
    private final SimpMessagingTemplate template;
    private final MemetickInventoryService inventoryService;
    private final NotifyBellService notifyBellService;
    private final OauthData oauthData;

    public NotifyCountService(
        NotifyWebService webService,
        SimpMessagingTemplate template,
        @Lazy MemetickInventoryService inventoryService,
        @Lazy NotifyBellService notifyBellService,
        OauthData oauthData
    ) {
        this.webService = webService;
        this.template = template;
        this.inventoryService = inventoryService;
        this.notifyBellService = notifyBellService;
        this.oauthData = oauthData;
    }

    public void ping(NotifyCountType type, NotifyCountAction action) {
        User currentUser = oauthData.getCurrentUser();
        String sessionId = webService.current();

        if (sessionId == null) return;

        NotifyCountDTO dto = new NotifyCountDTO(
            type,
            action
        );

        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);

        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);

        template.convertAndSendToUser(sessionId, "/queue/notify-count", dto, headerAccessor.getMessageHeaders());
    }

    public NotifyCountAPI get() {
        User user = oauthData.getCurrentUser();

        return new NotifyCountAPI(
            inventoryService.countItems(user.getMemetick()),
            notifyBellService.count(user)
        );
    }
}
