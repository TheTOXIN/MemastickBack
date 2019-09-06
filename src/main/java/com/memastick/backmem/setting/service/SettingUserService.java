package com.memastick.backmem.setting.service;

import com.memastick.backmem.security.component.OauthData;
import com.memastick.backmem.setting.api.SettingAPI;
import com.memastick.backmem.setting.entity.SettingUser;
import com.memastick.backmem.setting.repository.SettingUserRepository;
import com.memastick.backmem.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SettingUserService {

    private final SettingUserRepository settingUserRepository;
    private final OauthData oauthData;

    public void generateSetting(User user) {
        settingUserRepository.save(new SettingUser(user));
    }

    public SettingAPI mySetting() {
        return new SettingAPI(pushWork(
            oauthData.getCurrentUser()
        ));
    }

    public boolean pushWork(User user) {
        return settingUserRepository.findPushWorkByUserId(
            user.getId()
        );
    }

    @CacheEvict(value = "pushWork", key = "#user.id")
    public void pushSet(User user, boolean value) {
        SettingUser setting = settingUserRepository.findByUserId(user.getId());
        setting.setPushWork(value);
        settingUserRepository.save(setting);
    }
}
