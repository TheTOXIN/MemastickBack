package com.memastick.backmem.main.service;


import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.repository.MemetickRepository;
import com.memastick.backmem.memetick.service.MemetickAvatarService;
import com.memastick.backmem.memetick.service.MemetickInventoryService;
import com.memastick.backmem.security.constant.RoleType;
import com.memastick.backmem.setting.service.SettingUserService;
import com.memastick.backmem.user.entity.User;
import com.memastick.backmem.user.repository.UserRepository;
import com.memastick.backmem.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class Initializer {

    private final static String ADMIN_EMAIL = "admin@admin.admin";
    private final static String ADMIN_NICK = "АДМИН";

    private final Logger log = LoggerFactory.getLogger(Initializer.class);

    private final MemetickRepository memetickRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final MemetickInventoryService inventoryService;
    private final MemetickAvatarService avatarService;
    private final SettingUserService settingService;

    @Value("${memastick.admin.login}")
    private String adminLogin;

    @Value("${memastick.admin.password}")
    private String adminPassword;

    @Autowired
    public Initializer(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        UserService userService,
        MemetickRepository memetickRepository,
        MemetickInventoryService inventoryService,
        MemetickAvatarService avatarService,
        SettingUserService settingService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.memetickRepository = memetickRepository;
        this.inventoryService = inventoryService;
        this.avatarService = avatarService;
        this.settingService = settingService;
    }

    public void init() {
        log.info("-=START INIT=-");
        createAdmin();
        log.info("-=END INIT=-");
    }

    private void createAdmin() {
        User admin = userService.findAdmin();

        if (admin != null) {
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setLogin(adminLogin);
            userRepository.save(admin);
            return;
        }

        User user = new User();
        Memetick memetick = makeAdminMemetick();

        user.setEmail(ADMIN_EMAIL);
        user.setLogin(adminLogin);
        user.setPassword(passwordEncoder.encode(adminPassword));
        user.setRole(RoleType.ADMIN);
        user.setMemetick(memetick);

        userRepository.save(user);

        settingService.generateSetting(user);
        avatarService.generateAvatar(memetick);
        inventoryService.generateInventory(memetick);
    }

    private Memetick makeAdminMemetick() {
        Memetick memetick = new Memetick();

        memetick.setNick(ADMIN_NICK);
        memetickRepository.save(memetick);

        return memetick;
    }
}
