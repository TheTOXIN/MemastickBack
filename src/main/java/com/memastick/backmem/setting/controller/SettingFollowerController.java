package com.memastick.backmem.setting.controller;

import com.memastick.backmem.memetick.api.MemetickPreviewAPI;
import com.memastick.backmem.setting.service.SettingFollowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("setting-followers")
public class SettingFollowerController {

    private final SettingFollowerService settingFollowerService;

    @PostMapping("/follow/{memetickId}")
    public void follow(@PathVariable("memetickId")UUID memetickId) {
        settingFollowerService.trigger(memetickId);
    }

    @GetMapping("/my")
    public List<MemetickPreviewAPI> myFollowing() {
        return settingFollowerService.following();
    }
}
