package com.memastick.backmem.security.component;

import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.user.entity.User;
import com.memastick.backmem.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


@Component
public class OauthData {
    
    private final UserService userService;

    @Autowired
    public OauthData(@Lazy UserService userService) {
        this.userService = userService;
    }

    public UserDetails getCurrentDetails() {
        return (UserDetails) SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getPrincipal();
    }

    public User getCurrentUser() {
        UserDetails currentDetails = getCurrentDetails();
        return userService.findByLogin(currentDetails.getUsername());
    }

    public Memetick getCurrentMemetick() {
        return getCurrentUser().getMemetick();
    }
}
