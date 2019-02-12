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
        Optional<User> userOptional;

        if (username.contains("@")) {
            userOptional = userRepository.findByEmail(username);
        } else {
            userOptional = userRepository.findByLogin(username);
        }

        if (userOptional.isPresent()) {
            return makeUserDetails(userOptional.get());
        } else {
            throw new UsernameNotFoundException(String.format("User %s - does not exist!", username));
        }
    }

    private UserDetails makeUserDetails(User user) {
        return new org.springframework.security.core.userdetails.User(
            user.getLogin(),
            user.getPassword(),
            Collections.singletonList((new SimpleGrantedAuthority(user.getRole().name())))
        );
    }

}
