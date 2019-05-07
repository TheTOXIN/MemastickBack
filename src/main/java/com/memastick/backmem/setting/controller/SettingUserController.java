package com.memastick.backmem.setting.controller;

import com.memastick.backmem.setting.api.SettingAPI;
import com.memastick.backmem.setting.service.SettingUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("setting-users")
public class SettingUserController {

    private final SettingUserService settingUserService;

    @Autowired
    public SettingUserController(SettingUserService settingUserService) {
        this.settingUserService = settingUserService;
    }

    @GetMapping("me")
    public SettingAPI mySetting() {
        return settingUserService.mySetting();
    }

    @PatchMapping("/push/trigger")
    public void pushTrigger() {
        settingUserService.pushTrigger();
    }
}
