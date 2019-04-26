package com.memastick.backmem.notification.service;

import com.memastick.backmem.notification.dto.PushNotificationDTO;
import com.memastick.backmem.notification.repository.PushTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final PushNotificationService pushService;
    private final BellNotificationService bellService;
    private final PushTokenRepository pushTokenRepository;

    @Autowired
    public NotificationService(
        PushNotificationService pushService,
        BellNotificationService bellService,
        PushTokenRepository pushTokenRepository
    ) {
        this.pushService = pushService;
        this.bellService = bellService;
        this.pushTokenRepository = pushTokenRepository;
    }

    public void notifyAllowance() {
        bellService.sendAllowance();
        // TODO refactor and send where false
        pushTokenRepository.findAll().forEach(pt ->
            pushService.sendPush(
                new PushNotificationDTO(
                    "Пришло пособие!",
                    "Заберите свои токены в пособие",
                    "https://www.memastick.ru/home"
                ),
                pt.getToken()
            )
        );
    }
}
