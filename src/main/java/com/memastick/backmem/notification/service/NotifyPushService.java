package com.memastick.backmem.notification.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;
import com.memastick.backmem.notification.dto.NotifyPushDTO;
import com.memastick.backmem.notification.entity.NotifyPush;
import com.memastick.backmem.notification.repository.NotifyPushRepository;
import com.memastick.backmem.security.service.SecurityService;
import com.memastick.backmem.user.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class NotifyPushService {

    private static final Logger log = LoggerFactory.getLogger(NotifyPushService.class);

    private final NotifyPushRepository notifyPushRepository;
    private final SecurityService securityService;

    @Autowired
    public NotifyPushService(
        @Value("${fcm.push.file}") String fcmFile,
        NotifyPushRepository notifyPushRepository,
        SecurityService securityService
    ) {
        try (InputStream service = Files.newInputStream(Paths.get(fcmFile))) {
            FirebaseApp.initializeApp(
                new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(service))
                    .build()
            );
        } catch (IOException e) {
            log.error("PUSH NOTIFICATION NOT INIT");
            e.printStackTrace();
        }

        this.notifyPushRepository = notifyPushRepository;
        this.securityService = securityService;
    }

    public void register(String token) {
        User user = securityService.getCurrentUser();

        NotifyPush pushToken = notifyPushRepository
            .findByUser(user)
            .orElse(new NotifyPush(
                user
            ));

        pushToken.setToken(token);

        notifyPushRepository.save(pushToken);
    }

    public void send(NotifyPushDTO dto, User user) {
        String token = notifyPushRepository.findTokenByUser(user);

        WebpushConfig config = WebpushConfig.builder()
            .setNotification(builder(dto).build())
            .build();

        Message message = Message.builder()
            .setToken(token)
            .setWebpushConfig(config)
            .build();

        sender(message);
    }

    private void sender(Message message) {
        try {
            FirebaseMessaging.getInstance()
                .sendAsync(message)
                .get();
        } catch (Exception e) {
            log.error("PUSH NOTIFICATION NOT SEND");
            e.printStackTrace();
        }
    }

    private WebpushNotification.Builder builder(NotifyPushDTO dto){
        WebpushNotification.Builder builder = WebpushNotification.builder();

        builder
            .setImage(dto.getIcon())
            .setTitle(dto.getTitle());

        return builder;
    }
}
