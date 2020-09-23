package com.memastick.backmem.notification.impl;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import com.memastick.backmem.main.constant.LinkConstant;
import com.memastick.backmem.notification.dto.NotifyDTO;
import com.memastick.backmem.notification.entity.NotifyPush;
import com.memastick.backmem.notification.iface.NotifySender;
import com.memastick.backmem.notification.repository.NotifyPushRepository;
import com.memastick.backmem.security.component.OauthData;
import com.memastick.backmem.setting.service.SettingUserService;
import com.memastick.backmem.user.entity.User;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.nio.charset.Charset.defaultCharset;

@Service
public class NotifyPushService implements NotifySender {

    private static final Logger log = LoggerFactory.getLogger(NotifyPushService.class);

    private final NotifyPushRepository notifyPushRepository;
    private final SettingUserService settingUserService;
    private final OauthData oauthData;

    @Autowired
    public NotifyPushService(
        @Value("${memastick.prod}") boolean isProd,
        @Value("${fcm.push.json}") String fcmJson,
        @Value("${fcm.push.file}") String fcmPath,
        NotifyPushRepository notifyPushRepository,
        SettingUserService settingUserService,
        OauthData oauthData
    ) {
        this.init(isProd, fcmJson, new ClassPathResource(fcmPath));

        this.notifyPushRepository = notifyPushRepository;
        this.settingUserService = settingUserService;
        this.oauthData = oauthData;
    }

    @Override
    public void send(List<User> users, NotifyDTO dto) {
        users
            .stream()
            .filter(Objects::nonNull)
            .filter(settingUserService::pushWork)
            .forEach(u -> send(dto, notifyPushRepository.findAllByUser(u)
                .stream()
                .map(NotifyPush::getToken)
                .collect(Collectors.toList())
            ));
    }

    private void send(NotifyDTO dto, List<String> tokens) {
        if (tokens.isEmpty()) return;

        WebpushConfig config = WebpushConfig.builder()
            .setNotification(builder(dto).build())
            .build();

        MulticastMessage messages = MulticastMessage.builder()
            .addAllTokens(tokens)
            .setWebpushConfig(config)
            .build();

        try {
            BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(messages);
            log.info("PUSH NOTIFICATION SEND: DTO - {}, RESPONSE - {}", dto, response);
        } catch (Exception e) {
            log.error("PUSH NOTIFICATION NOT SEND: DTO - {}", dto);
            e.printStackTrace();
        }
    }

    private WebpushNotification.Builder builder(NotifyDTO dto) {
        return WebpushNotification.builder()
            .putCustomData("click_action", dto.getEvent())
            .setIcon(LinkConstant.LINK_ICON)
            .setTitle(dto.getTitle())
            .setBody(dto.getText());
    }

    public void register(String token) {
        User user = oauthData.getCurrentUser();

        NotifyPush notifyPush = notifyPushRepository
            .findByToken(token)
            .orElse(new NotifyPush(token));

        if (user.equals(notifyPush.getUser())) return;
        notifyPush.setUser(user);

        notifyPushRepository.save(notifyPush);
    }

    public void refresher(String oldToken, String newToken) {
        Optional<NotifyPush> optional = notifyPushRepository.findByToken(oldToken);

        if (optional.isEmpty()) return;
        NotifyPush notifyPush = optional.get();
        notifyPush.setToken(newToken);

        notifyPushRepository.save(notifyPush);
    }

    private void init(boolean isProd, String fcmJson, Resource fcmFile) {
        try {
            InputStream stream = isProd ?
                IOUtils.toInputStream(fcmJson, defaultCharset()) :
                fcmFile.getInputStream();

            FirebaseApp.initializeApp(
                new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(stream))
                    .build()
            );
        } catch (Exception e) {
            log.error("PUSH NOTIFICATION NOT INIT");
            e.printStackTrace();
        }
    }
}
