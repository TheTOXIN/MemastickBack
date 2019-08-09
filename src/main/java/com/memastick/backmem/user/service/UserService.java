package com.memastick.backmem.user.service;

import com.memastick.backmem.errors.exception.EntityNotFoundException;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.service.MemetickAvatarService;
import com.memastick.backmem.memetick.service.MemetickInventoryService;
import com.memastick.backmem.memetick.service.MemetickService;
import com.memastick.backmem.security.api.RegistrationAPI;
import com.memastick.backmem.security.component.OauthData;
import com.memastick.backmem.security.constant.RoleType;
import com.memastick.backmem.setting.service.SettingUserService;
import com.memastick.backmem.tokens.service.TokenWalletService;
import com.memastick.backmem.user.api.MeAPI;
import com.memastick.backmem.user.entity.User;
import com.memastick.backmem.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemetickInventoryService inventoryService;
    private final MemetickAvatarService avatarService;
    private final SettingUserService settingService;
    private final TokenStore tokenStore;
    private final OauthData oauthData;
    private final MemetickService memetickService;
    private final TokenWalletService walletService;

    @Value("${oauth.client}")
    private String oauthClient;

    public User findAdmin() {
        Optional<User> byRole = userRepository.findByRole(RoleType.ADMIN);
        return byRole.orElse(null);
    }

    @Transactional
    public User generateUser(RegistrationAPI request, String nick) {
        User user = new User();

        Memetick memetick = memetickService.generateMemetick(nick);

        user.setEmail(request.getEmail());
        user.setLogin(request.getLogin());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(RoleType.USER);
        user.setMemetick(memetick);

        userRepository.save(user);
        generateDependencies(user);

        return user;
    }

    public void generateDependencies(User user) {
        settingService.generateSetting(user);
        avatarService.generateAvatar(user.getMemetick());
        walletService.generateWallet(user.getMemetick());
        inventoryService.generateInventory(user.getMemetick());
    }

    public void updatePassword(String login, String password) {
        User user = findByLogin(login);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    public User findByLogin(String login) {
        Optional<User> byLogin = userRepository.findByLoginWithCache(login);
        if (byLogin.isEmpty()) throw new EntityNotFoundException(User.class, "login");
        return byLogin.get();
    }

    public boolean isOnline(Memetick memetick) {
        var user = userRepository.findByMemetick(memetick);
        var tokens = tokenStore.findTokensByClientIdAndUserName(oauthClient, user.getLogin());

        return tokens.stream().anyMatch(token -> !token.isExpired());
    }

    public MeAPI me() {
        User user = oauthData.getCurrentUser();

        return new MeAPI(
            user.getId(),
            user.getLogin(),
            user.getRole()
        );
    }
}
