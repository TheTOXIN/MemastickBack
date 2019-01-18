package com.memastick.backmem.security.controller;

import com.memastick.backmem.security.api.PasswordResetSendAPI;
import com.memastick.backmem.security.api.PasswordResetTakeAPI;
import com.memastick.backmem.security.constant.SecurityStatus;
import com.memastick.backmem.security.service.PasswordResetService;
import com.memastick.backmem.sender.dto.EmailStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("password-reset")
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    @Autowired
    public PasswordResetController(
        PasswordResetService passwordResetService
    ) {
        this.passwordResetService = passwordResetService;
    }

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
