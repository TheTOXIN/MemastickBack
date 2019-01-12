package com.memastick.backmem.person.service;

import com.memastick.backmem.code.entity.InviteCode;
import com.memastick.backmem.person.entity.Memetick;
import com.memastick.backmem.security.api.RegistrationAPI;
import com.memastick.backmem.security.constant.RoleType;
import com.memastick.backmem.person.entity.User;
import com.memastick.backmem.person.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemetickService memetickService;

    @Autowired
    public UserService(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        MemetickService memetickService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.memetickService = memetickService;
    }

    public User findAdmin() {
        Optional<User> byRole = userRepository.findByRole(RoleType.ADMIN);
        return byRole.orElse(null);
    }

    public User generateUser(RegistrationAPI request, InviteCode inviteCode) {
        User user = new User();

        user.setEmail(request.getEmail());
        user.setLogin(request.getLogin());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(RoleType.USER);

        Memetick memetick = memetickService.generateMemetick(inviteCode.getNick());

        user.setMemetick(memetick);

        return userRepository.save(user);
    }

}
