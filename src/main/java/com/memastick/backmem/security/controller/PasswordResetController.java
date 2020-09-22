package com.memastick.backmem.security.controller;

import com.memastick.backmem.security.api.PasswordResetSendAPI;
import com.memastick.backmem.security.api.PasswordResetTakeAPI;
import com.memastick.backmem.security.constant.SecurityStatus;
import com.memastick.backmem.security.service.PasswordResetService;
import com.memastick.backmem.sender.dto.EmailStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("password-reset")
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    @PatchMapping("send")
    public ResponseEntity passwordReset(@RequestBody PasswordResetSendAPI request) {
        EmailStatus status = passwordResetService.send(request.getEmail());
        if (status.isSuccess()) return ResponseEntity.ok().build();
        return ResponseEntity.unprocessableEntity().build();
    }

    @PatchMapping("take")
    public ResponseEntity<SecurityStatus> passwordReset(@RequestBody PasswordResetTakeAPI request) {
        SecurityStatus status = passwordResetService.take(request);
        if (status.equals(SecurityStatus.SUCCESSFUL)) return ResponseEntity.ok(status);
        return ResponseEntity.unprocessableEntity().body(status);
    }
}
