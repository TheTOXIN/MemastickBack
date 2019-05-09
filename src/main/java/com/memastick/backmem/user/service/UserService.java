package com.memastick.backmem.user.service;

import com.memastick.backmem.errors.exception.EntityNotFoundException;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.repository.MemetickAvatarRepository;
import com.memastick.backmem.memetick.repository.MemetickRepository;
import com.memastick.backmem.memetick.service.MemetickAvatarService;
import com.memastick.backmem.memetick.service.MemetickInventoryService;
import com.memastick.backmem.security.api.RegistrationAPI;
import com.memastick.backmem.security.constant.RoleType;
import com.memastick.backmem.security.entity.InviteCode;
import com.memastick.backmem.setting.entity.SettingUser;
import com.memastick.backmem.setting.service.SettingUserService;
import com.memastick.backmem.user.entity.User;
import com.memastick.backmem.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemetickRepository memetickRepository;
    private final MemetickInventoryService inventoryService;
    private final MemetickAvatarService avatarService;
    private final SettingUserService settingService;

    @Autowired
    public UserService(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        MemetickRepository memetickRepository,
        MemetickInventoryService inventoryService,
        MemetickAvatarService avatarService,
        SettingUserService settingService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.memetickRepository = memetickRepository;
        this.inventoryService = inventoryService;
        this.avatarService = avatarService;
        this.settingService = settingService;
    }

    public User findAdmin() {
        Optional<User> byRole = userRepository.findByRole(RoleType.ADMIN);
        return byRole.orElse(null);
    }

    @Transactional
    public User generateUser(RegistrationAPI request, InviteCode inviteCode) {
        User user = new User();

        user.setEmail(request.getEmail());
        user.setLogin(request.getLogin());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(RoleType.USER);

        Memetick memetick = new Memetick();
        memetick.setNick(inviteCode.getNick());
        memetickRepository.save(memetick);

        user.setMemetick(memetick);

        avatarService.generateAvatar(memetick);
        inventoryService.generateInventory(memetick);

        user = userRepository.save(user);

        settingService.generateSetting(user);

        return user;
    }

    public void updatePassword(String login, String password) {
        User user = findByLogin(login);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    public User findByLogin(String login) {
        Optional<User> byLogin = userRepository.findByLogin(login);
        if (byLogin.isEmpty()) throw new EntityNotFoundException(User.class, "login");
        return byLogin.get();
    }
}
