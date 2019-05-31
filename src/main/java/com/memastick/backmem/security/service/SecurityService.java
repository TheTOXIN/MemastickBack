package com.memastick.backmem.security.service;

import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.user.entity.User;
import com.memastick.backmem.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@Service
public class SecurityService {

    private final UserService userService;

    @Autowired
    public SecurityService(
        @Lazy UserService userService
    ) {
        this.userService = userService;
    }

    public UserDetails getCurrentDetails() {
        return (UserDetails) SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getPrincipal();
    }

    //TODO cache
    public User getCurrentUser() {
        UserDetails currentDetails = getCurrentDetails();
        return userService.findByLogin(currentDetails.getUsername());
    }

    public Memetick getCurrentMemetick() {
        return getCurrentUser().getMemetick();
    }
}
