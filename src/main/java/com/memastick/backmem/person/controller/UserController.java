package com.memastick.backmem.person.controller;

import com.memastick.backmem.person.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(
        UserService userService
    ) {
        this.userService = userService;
    }

    @GetMapping("login/memtetick/{memetickId}")
    public String readLogin(@PathVariable("memetickId") UUID memetickId) {
        return userService.readLoginByMemetickId(memetickId);
    }

}
