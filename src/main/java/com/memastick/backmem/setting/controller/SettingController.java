package com.memastick.backmem.setting.controller;

import com.memastick.backmem.security.service.SecurityService;
import com.memastick.backmem.setting.api.SettingAPI;
import com.memastick.backmem.setting.service.SettingFollowerService;
import com.memastick.backmem.setting.service.SettingUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("setting")
public class SettingController {

    private final SettingFollowerService settingFollowerService;
    private final SettingUserService settingUserService;
    private final SecurityService securityService;

    @Autowired
    public SettingController(
        SettingFollowerService settingFollowerService,
        SettingUserService settingUserService,
        SecurityService securityService
    ) {
        this.settingFollowerService = settingFollowerService;
        this.settingUserService = settingUserService;
        this.securityService = securityService;
    }

    @PostMapping("/follow/{memetickId}")
    public void follow(@PathVariable("memetickId")UUID memetickId) {
        settingFollowerService.trigger(memetickId);
    }

    @GetMapping("/me")
    public SettingAPI me() {
        return settingUserService.mySetting();
    }

    @PatchMapping("/push/{value}")
    public void pushSet(@PathVariable("value") boolean value) {
        settingUserService.pushSet(
            securityService.getCurrentUser(),
            value
        );
    }
}
