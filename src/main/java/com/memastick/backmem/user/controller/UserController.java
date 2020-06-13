package com.memastick.backmem.user.controller;

import com.memastick.backmem.user.api.MeAPI;
import com.memastick.backmem.user.api.UserDataAPI;
import com.memastick.backmem.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("me")
    public MeAPI me() {
        return userService.me();
    }

    @GetMapping("data")
    public UserDataAPI userData() {
        return userService.data();
    }
}
