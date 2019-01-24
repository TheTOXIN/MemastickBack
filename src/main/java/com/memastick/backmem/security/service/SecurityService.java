package com.memastick.backmem.security.service;

import com.memastick.backmem.security.model.MyUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    public MyUserDetails getCurrentUser() {
        return (MyUserDetails) SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getPrincipal();
    }
}
