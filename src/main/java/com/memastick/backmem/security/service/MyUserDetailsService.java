package com.memastick.backmem.security.service;

import com.memastick.backmem.user.entity.User;
import com.memastick.backmem.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;


@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> optional;

        if (username.contains("@")) {
            optional = userRepository.findByEmail(username);
        } else {
            optional = userRepository.findByLogin(username);
        }

        User user = optional.orElseThrow(() -> new UsernameNotFoundException("User " + username + "does not exist!"));

        return makeUserDetails(user);
    }

    private UserDetails makeUserDetails(User user) {
        return new org.springframework.security.core.userdetails.User(
            user.getLogin(),
            user.getPassword(),
            true, true, true, !user.isBan(),
            Collections.singletonList((new SimpleGrantedAuthority(user.getRole().name())))
        );
    }
}
