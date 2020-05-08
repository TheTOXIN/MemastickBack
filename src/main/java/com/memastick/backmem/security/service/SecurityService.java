package com.memastick.backmem.security.service;

import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.notification.entity.NotifyPush;
import com.memastick.backmem.notification.impl.NotifyWebService;
import com.memastick.backmem.notification.repository.NotifyPushRepository;
import com.memastick.backmem.security.api.LogOutAPI;
import com.memastick.backmem.security.component.OauthData;
import com.memastick.backmem.user.entity.User;
import com.memastick.backmem.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final NotifyPushRepository pushRepository;
    private final NotifyWebService notifyWebService;
    private final UserRepository userRepository;
    private final TokenStore tokenStore;
    private final OauthData oauthData;

    @Value("${oauth.client}")
    private String oauthClient;

    public void logout(LogOutAPI request) {
        Optional<NotifyPush> byToken = pushRepository.findByToken(request.getDeviceToken());
        byToken.ifPresent(pushRepository::delete);
        notifyWebService.remove();
    }

    public void ban(String login) {
        User user = userRepository.tryFindByLogin(login);

        user.setBan(true);
        userRepository.save(user);

        clear(user);
    }

    private void clear(User user) {
        tokenStore.findTokensByClientIdAndUserName(oauthClient, user.getLogin()).forEach(t -> {
            tokenStore.removeAccessToken(t);
            tokenStore.removeRefreshToken(t.getRefreshToken());
        });
    }

    public boolean isOnline(Memetick memetick) {
        Memetick currentMemetick = oauthData.getCurrentMemetick();

        if (memetick.getId().equals(currentMemetick.getId())) return true;

        var user = userRepository.findByMemetick(memetick);
        var tokens = tokenStore.findTokensByClientIdAndUserName(oauthClient, user.getLogin());

        return tokens.stream().anyMatch(token -> !token.isExpired());
    }
}
