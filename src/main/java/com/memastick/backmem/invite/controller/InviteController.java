package com.memastick.backmem.invite.controller;

import com.memastick.backmem.invite.api.InviteAPI;
import com.memastick.backmem.invite.entity.Invite;
import com.memastick.backmem.invite.service.InviteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class InviteController {

    private final InviteService inviteService;

    @Autowired
    public InviteController(InviteService inviteService) {
        this.inviteService = inviteService;
    }

    @PostMapping("invite/registration")
    public ResponseEntity register(@RequestBody InviteAPI request) {
        inviteService.register(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("invites")
    public ResponseEntity<List<Invite>> readAll() {
        return ResponseEntity.ok(inviteService.readAll());
    }

    @GetMapping("invite/{code}")
    public ResponseEntity<Invite> readByCode(@PathVariable("code") String code) {
        return ResponseEntity.of(inviteService.findByCode(code));
    }

}
