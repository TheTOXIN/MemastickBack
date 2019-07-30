package com.memastick.backmem.setting.controller;

import com.memastick.backmem.security.component.OauthData;
import com.memastick.backmem.setting.api.SettingAPI;
import com.memastick.backmem.setting.service.SettingUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("setting-users")
public class SettingUserController {

    private final SettingUserService settingUserService;
    private final OauthData oauthData;

    @Autowired
    public SettingUserController(
        SettingUserService settingUserService,
        OauthData oauthData
    ) {
        this.settingUserService = settingUserService;
        this.oauthData = oauthData;
    }

    @GetMapping("/me")
    public SettingAPI me() {
        return settingUserService.mySetting();
    }

    @PatchMapping("/push/{value}")
    public void push(@PathVariable("value") boolean value) {
        settingUserService.pushSet(
            oauthData.getCurrentUser(),
            value
        );
    }
}
