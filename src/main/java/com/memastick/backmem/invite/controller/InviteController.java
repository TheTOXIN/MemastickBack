package com.memastick.backmem.invite.controller;

import com.memastick.backmem.invite.api.InviteAPI;
import com.memastick.backmem.invite.service.InviteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController("invite")
public class InviteController {

    private final InviteService inviteService;

    @Autowired
    public InviteController(InviteService inviteService) {
        this.inviteService = inviteService;
    }

    @PostMapping("register")
    public ResponseEntity register(@RequestBody InviteAPI request) {
        inviteService.register(request);
        return ResponseEntity.ok().build();
    }

}
