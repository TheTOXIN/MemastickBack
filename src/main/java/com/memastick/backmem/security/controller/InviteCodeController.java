package com.memastick.backmem.security.controller;

import com.memastick.backmem.security.api.InviteCodeAPI;
import com.memastick.backmem.security.entity.InviteCode;
import com.memastick.backmem.security.repository.InviteCodeRepository;
import com.memastick.backmem.security.service.InviteCodeService;
import com.memastick.backmem.sender.dto.EmailStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class InviteCodeController {

    private final InviteCodeService inviteCodeService;
    private final InviteCodeRepository inviteCodeRepository;

    @Autowired
    public InviteCodeController(
        InviteCodeService inviteCodeService,
        InviteCodeRepository inviteCodeRepository
    ) {
        this.inviteCodeService = inviteCodeService;
        this.inviteCodeRepository = inviteCodeRepository;
    }

    @PostMapping("invite/registration")
    public ResponseEntity register(@RequestBody InviteCodeAPI request) {
        inviteCodeService.register(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("invites")
    public ResponseEntity<List<InviteCode>> readAll() {
        return ResponseEntity.ok(inviteCodeRepository.findAll());
    }

    @GetMapping("invite/{code}")
    public ResponseEntity<InviteCode> readByCode(@PathVariable("code") String code) {
        return ResponseEntity.of(inviteCodeRepository.findByCode(code));
    }

    @PatchMapping("invite/send/{code}")
    public ResponseEntity send(@PathVariable("code") String code) {
        EmailStatus status = inviteCodeService.send(code);
        if (status.isSuccess()) return ResponseEntity.ok().build();
        else return ResponseEntity.unprocessableEntity().build();
    }

    @PatchMapping("invite/take/{code}")
    public ResponseEntity take(@PathVariable("code") String code) {
        inviteCodeService.take(code);
        return ResponseEntity.ok().build();
    }

}
