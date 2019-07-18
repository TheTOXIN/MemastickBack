package com.memastick.backmem.security.controller;

import com.memastick.backmem.security.api.RegistrationAPI;
import com.memastick.backmem.security.constant.SecurityStatus;
import com.memastick.backmem.security.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RegistrationController {

    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(
        RegistrationService registrationService
    ) {
        this.registrationService = registrationService;
    }

    @PostMapping("registration")
    public ResponseEntity<SecurityStatus> registration(@RequestBody RegistrationAPI request) {
        SecurityStatus status = registrationService.registration(request);
        if (status.equals(SecurityStatus.SUCCESSFUL)) return ResponseEntity.ok(status);
        return ResponseEntity.unprocessableEntity().body(status);
    }
}
