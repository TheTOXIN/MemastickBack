package com.memastick.backmem.security.service;

import com.memastick.backmem.security.model.MyUserDetails;
import com.memastick.backmem.person.entity.User;
import com.memastick.backmem.person.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
            return new MyUserDetails(userOptional.get());
        } else {
            throw new UsernameNotFoundException(String.format("User %s - does not exist!", username));
        }
    }

}
