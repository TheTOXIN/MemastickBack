package com.memastick.backmem.security.service;

import com.memastick.backmem.notification.entity.NotifyPush;
import com.memastick.backmem.notification.impl.NotifyWebService;
import com.memastick.backmem.notification.repository.NotifyPushRepository;
import com.memastick.backmem.security.api.LogOutAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SecurityService {

    private final NotifyPushRepository pushRepository;
    private final NotifyWebService notifyWebService;

    @Autowired
    public SecurityService(
        NotifyPushRepository pushRepository,
        NotifyWebService notifyWebService
    ) {
        this.pushRepository = pushRepository;
        this.notifyWebService = notifyWebService;
    }

    public void logout(LogOutAPI request) {
        Optional<NotifyPush> byToken = pushRepository.findByToken(request.getDeviceToken());
        byToken.ifPresent(pushRepository::delete);
        notifyWebService.remove();
    }
}
