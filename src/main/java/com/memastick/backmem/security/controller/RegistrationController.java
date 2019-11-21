package com.memastick.backmem.security.controller;

import com.memastick.backmem.security.api.RegistrationAPI;
import com.memastick.backmem.security.constant.SecurityStatus;
import com.memastick.backmem.security.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("registration")
    public ResponseEntity<SecurityStatus> registration(@RequestBody RegistrationAPI request) {
        SecurityStatus status = registrationService.registration(request);
        if (status.equals(SecurityStatus.SUCCESSFUL)) return ResponseEntity.ok(status);
        return ResponseEntity.unprocessableEntity().body(status);
    }
}
