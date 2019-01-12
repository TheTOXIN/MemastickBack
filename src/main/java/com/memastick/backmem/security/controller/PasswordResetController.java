package com.memastick.backmem.security.controller;

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
    public ResponseEntity passwordReset(@RequestBody String email) {
        EmailStatus status = passwordResetService.send(email);
        if (status.isSuccess()) return ResponseEntity.ok().build();
        else return ResponseEntity.unprocessableEntity().build();
    }

    @PatchMapping("take")
    public SecurityStatus passwordReset(@RequestBody PasswordResetTakeAPI request) {
        return passwordResetService.take(request);
    }

}
