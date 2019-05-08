package com.memastick.backmem.setting.controller;

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

    @Autowired
    public SettingController(
        SettingFollowerService settingFollowerService,
        SettingUserService settingUserService
    ) {
        this.settingFollowerService = settingFollowerService;
        this.settingUserService = settingUserService;
    }

    @PostMapping("/follow/{memetickId}")
    public void follow(@PathVariable("memetickId")UUID memetickId) {
        settingFollowerService.trigger(memetickId);
    }

    @GetMapping("/me")
    public SettingAPI me() {
        return settingUserService.mySetting();
    }
}
