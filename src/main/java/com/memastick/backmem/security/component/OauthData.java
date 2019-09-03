package com.memastick.backmem.security.component;

import com.memastick.backmem.errors.exception.EntityNotFoundException;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.user.entity.User;
import com.memastick.backmem.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class OauthData {
    
    private final UserRepository userRepository;

    public UserDetails getCurrentDetails() {
        return (UserDetails) SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getPrincipal();
    }

    public User getCurrentUser() {
        return userRepository
            .findByLoginCache(getCurrentDetails().getUsername())
            .orElseThrow(() -> new EntityNotFoundException(User.class, "current username"));
    }

    public Memetick getCurrentMemetick() {
        return getCurrentUser().getMemetick();
    }
}
