package com.memastick.backmem.notification.service;

import com.memastick.backmem.notification.entity.PushToken;
import com.memastick.backmem.notification.repository.PushTokenRepository;
import com.memastick.backmem.security.service.SecurityService;
import com.memastick.backmem.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PushTokenService {

    private final PushTokenRepository pushTokenRepository;
    private final SecurityService securityService;

    @Autowired
    public PushTokenService(
        PushTokenRepository pushTokenRepository,
        SecurityService securityService
    ) {
        this.pushTokenRepository = pushTokenRepository;
        this.securityService = securityService;
    }

    public void update(String token) {
        User user = securityService.getCurrentUser();

        PushToken pushToken = pushTokenRepository
            .findByUser(user)
            .orElse(make(user));

        pushToken.setToken(token);

        pushTokenRepository.save(pushToken);
    }

    private PushToken make(User user) {
        return new PushToken(
            user
        );
    }
}
