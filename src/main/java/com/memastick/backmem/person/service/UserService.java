package com.memastick.backmem.person.service;

import com.memastick.backmem.security.constant.RoleType;
import com.memastick.backmem.person.entity.User;
import com.memastick.backmem.person.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(
        UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    public User findAdmin() {
        Optional<User> byRole = userRepository.findByRole(RoleType.ADMIN);
        return byRole.orElse(null);
    }

}
