package com.memastick.backmem.security.service;

import com.memastick.backmem.person.entity.Memetick;
import com.memastick.backmem.person.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@Service
public class SecurityService {

    @Autowired
    private UserService userService;

    public UserDetails getCurrentUser() {
        return (UserDetails) SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getPrincipal();
    }

    public Memetick getCurrentMemetick() {
        UserDetails userDetails = getCurrentUser();
        return userService.findByLogin(userDetails.getUsername()).getMemetick();
    }

}
