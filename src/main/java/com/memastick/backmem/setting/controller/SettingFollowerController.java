package com.memastick.backmem.setting.controller;

import com.memastick.backmem.setting.service.SettingFollowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("setting-followers")
public class SettingFollowerController {

    private final SettingFollowerService settingFollowerService;

    @Autowired
    public SettingFollowerController(SettingFollowerService settingFollowerService) {
        this.settingFollowerService = settingFollowerService;
    }

    @PostMapping("/trigger/{memetickId}")
    public void trigger(@PathVariable("memetickId")UUID memetickId) {
        settingFollowerService.trigger(memetickId);
    }
}
