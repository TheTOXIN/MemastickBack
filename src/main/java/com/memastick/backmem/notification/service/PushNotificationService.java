package com.memastick.backmem.notification.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;
import com.memastick.backmem.notification.dto.PushNotificationDTO;
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
public class PushNotificationService {

    private static final Logger log = LoggerFactory.getLogger(PushNotificationService.class);

    @Autowired
    public PushNotificationService(
        @Value("${fcm.push.file}") String fcmFile
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
    }


    public void sendPush(PushNotificationDTO dto, String token) {
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

    private WebpushNotification.Builder builder(PushNotificationDTO dto){
        WebpushNotification.Builder builder = WebpushNotification.builder();

        WebpushNotification.Action action = new WebpushNotification.Action(
            dto.getAction(),
            "ОТКРЫТЬ"
        );

        builder
            .addAction(action)
            .setImage(dto.getIcon())
            .setTitle(dto.getTitle())
            .setBody(dto.getBody());

        return builder;
    }
}
