package com.memastick.backmem.notification.impl;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;
import com.memastick.backmem.notification.constant.NotifyConstant;
import com.memastick.backmem.notification.dto.NotifyDTO;
import com.memastick.backmem.notification.entity.NotifyPush;
import com.memastick.backmem.notification.iface.NotifySender;
import com.memastick.backmem.notification.repository.NotifyPushRepository;
import com.memastick.backmem.security.service.SecurityService;
import com.memastick.backmem.setting.service.SettingUserService;
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
import java.util.List;

@Service
public class NotifyPushService implements NotifySender {

    private static final Logger log = LoggerFactory.getLogger(NotifyPushService.class);

    private final NotifyPushRepository notifyPushRepository;
    private final SecurityService securityService;
    private final SettingUserService settingUserService;

    @Autowired
    public NotifyPushService(
        @Value("${fcm.push.file}") String fcmFile,
        NotifyPushRepository notifyPushRepository,
        SecurityService securityService,
        SettingUserService settingUserService
    ) {
        this.init(fcmFile);
        this.notifyPushRepository = notifyPushRepository;
        this.securityService = securityService;
        this.settingUserService = settingUserService;
    }

    @Override
    public void send(List<User> users, NotifyDTO dto) {
        users
            .stream()
            .filter(settingUserService::pushWork)
            .forEach(u ->
                notifyPushRepository.findAllByUser(u).forEach(p ->
                    send(dto, p.getToken())
                )
            );
    }

    private void send(NotifyDTO dto, String token) {
        WebpushConfig config = WebpushConfig.builder()
            .setNotification(builder(dto).build())
            .build();

        Message message = Message.builder()
            .setToken(token)
            .setWebpushConfig(config)
            .build();

        try {
            FirebaseMessaging.getInstance()
                .sendAsync(message)
                .get();
        } catch (Exception e) {
            log.error("PUSH NOTIFICATION NOT SEND");
            e.printStackTrace();
        }
    }

    private WebpushNotification.Builder builder(NotifyDTO dto){
        WebpushNotification.Builder builder = WebpushNotification.builder();

        WebpushNotification.Action action = new WebpushNotification.Action(
            dto.getEvent(),
            "ОТКРЫТЬ"
        );

        builder
            .addAction(action)
            .setImage(NotifyConstant.LINK_ICON)
            .setTitle(dto.getTitle())
            .setBody(dto.getText());

        return builder;
    }

    public void register(String token) {
        User user = securityService.getCurrentUser();

        NotifyPush notifyPush = notifyPushRepository
            .findByToken(token)
            .orElse(new NotifyPush(token));

        settingUserService.pushOn(user);

        if (user.equals(notifyPush.getUser())) return;

        notifyPush.setUser(user);

        notifyPushRepository.save(notifyPush);
    }

    public void unregister() {
        User user = securityService.getCurrentUser();
        settingUserService.pushOff(user);
        List<NotifyPush> pushes = notifyPushRepository.findAllByUser(user);
        notifyPushRepository.deleteAll(pushes);
    }

    private void init(String fcmFile) {
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
}
