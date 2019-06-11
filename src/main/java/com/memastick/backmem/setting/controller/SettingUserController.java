package com.memastick.backmem.setting.controller;

import com.memastick.backmem.security.service.SecurityService;
import com.memastick.backmem.setting.api.SettingAPI;
import com.memastick.backmem.setting.service.SettingUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("setting-users")
public class SettingUserController {

    private final SettingUserService settingUserService;
    private final SecurityService securityService;

    @Autowired
    public SettingUserController(
        SettingUserService settingUserService,
        SecurityService securityService
    ) {
        this.settingUserService = settingUserService;
        this.securityService = securityService;
    }

    @GetMapping("/me")
    public SettingAPI me() {
        return settingUserService.mySetting();
    }

    @PatchMapping("/push/{value}")
    public void push(@PathVariable("value") boolean value) {
        settingUserService.pushSet(
            securityService.getCurrentUser(),
            value
        );
    }
}
