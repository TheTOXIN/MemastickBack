package com.memastick.backmem.security.controller;

import com.memastick.backmem.security.api.LogOutAPI;
import com.memastick.backmem.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("security")
public class SecurityController {

    private final SecurityService securityService;

    @Autowired
    public SecurityController(
        SecurityService securityService
    ) {
        this.securityService = securityService;
    }

    @PostMapping("logout")
    public void logOut(@RequestBody LogOutAPI request) {
        securityService.logout(request);
    }
}
