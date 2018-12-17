package com.memastick.backmem.main.service;


import com.memastick.backmem.security.constant.RoleType;
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

    private final Logger log = LoggerFactory.getLogger(Initializer.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Value("${memastick.admin.email}")
    private String adminEmail;

    @Value("${memastick.admin.password}")
    private String adminPassword;

    @Autowired
    public Initializer(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        UserService userService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    public void init() {
        log.info("-=START INIT=-");
        createAdmin();
        log.info("-=END INIT=-");
    }

    private void createAdmin() {
        User admin = userService.findAdmin();

        if (admin != null) return;

        User user = new User();

        user.setEmail(adminEmail);
        user.setHash(passwordEncoder.encode(adminPassword));
        user.setRole(RoleType.ADMIN);

        userRepository.save(user);
    }

}
