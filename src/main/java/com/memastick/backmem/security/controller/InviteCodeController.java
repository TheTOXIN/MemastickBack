package com.memastick.backmem.security.controller;

import com.memastick.backmem.security.api.InviteCodeAPI;
import com.memastick.backmem.security.entity.InviteCode;
import com.memastick.backmem.security.repository.InviteCodeRepository;
import com.memastick.backmem.security.service.InviteCodeService;
import com.memastick.backmem.sender.dto.EmailStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class InviteCodeController {

    private final InviteCodeService inviteCodeService;
    private final InviteCodeRepository inviteCodeRepository;

    @PostMapping("invite/registration")
    public ResponseEntity register(@RequestBody InviteCodeAPI request) {
        inviteCodeService.register(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("invites")
    public ResponseEntity<List<InviteCode>> readAll() {
        return ResponseEntity.ok(inviteCodeRepository.findAll(Sort.by("dateCreate").descending()));
    }

    @GetMapping("invite/{code}")
    public ResponseEntity<InviteCode> readByCode(@PathVariable("code") String code) {
        return ResponseEntity.of(inviteCodeRepository.findByCode(code));
    }

    @PatchMapping("invite/send")
    public ResponseEntity send(@RequestBody String code) {
        EmailStatus status = inviteCodeService.send(code);
        if (status.isSuccess()) return ResponseEntity.ok().build();
        else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PatchMapping("invite/take")
    public ResponseEntity take(@RequestBody String code) {
        inviteCodeService.take(code);
        return ResponseEntity.ok().build();
    }
}
