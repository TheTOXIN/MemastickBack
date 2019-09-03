package com.memastick.backmem.security.controller;

import com.memastick.backmem.security.api.LogOutAPI;
import com.memastick.backmem.security.service.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("security")
@AllArgsConstructor
public class SecurityController {

    private final SecurityService securityService;

    @PostMapping("logout")
    public void logOut(@RequestBody LogOutAPI request) {
        securityService.logout(request);
    }

    @PatchMapping("ban/{login}")
    public void ban(@PathVariable("login") String login) {
        securityService.ban(login);
    }
}
