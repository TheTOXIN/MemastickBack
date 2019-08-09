package com.memastick.backmem.main.service;


import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.repository.MemetickRepository;
import com.memastick.backmem.security.constant.RoleType;
import com.memastick.backmem.user.entity.User;
import com.memastick.backmem.user.repository.UserRepository;
import com.memastick.backmem.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class Initializer {

    private final static String ADMIN_EMAIL = "admin@admin.admin";
    private final static String ADMIN_NICK = "АДМИН";

    private final Logger log = LoggerFactory.getLogger(Initializer.class);

    private final MemetickRepository memetickRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Value("${memastick.admin.login}")
    private String adminLogin;

    @Value("${memastick.admin.password}")
    private String adminPassword;

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
        userService.generateDependencies(user);
    }

    private Memetick makeAdminMemetick() {
        Memetick memetick = new Memetick();

        memetick.setNick(ADMIN_NICK);
        memetickRepository.save(memetick);

        return memetick;
    }
}
