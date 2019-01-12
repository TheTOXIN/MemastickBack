package com.memastick.backmem.security.controller;

import com.memastick.backmem.security.api.RegistrationAPI;
import com.memastick.backmem.security.constant.SecurityStatus;
import com.memastick.backmem.security.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public SecurityStatus registration(@RequestBody RegistrationAPI request) {
        return registrationService.registration(request);
    }

}
