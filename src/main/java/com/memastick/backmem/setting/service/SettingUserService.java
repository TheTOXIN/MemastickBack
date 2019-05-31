package com.memastick.backmem.setting.service;

import com.memastick.backmem.security.service.SecurityService;
import com.memastick.backmem.setting.api.SettingAPI;
import com.memastick.backmem.setting.entity.SettingUser;
import com.memastick.backmem.setting.repository.SettingUserRepository;
import com.memastick.backmem.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingUserService {

    private final SettingUserRepository settingUserRepository;
    private final SecurityService securityService;

    @Autowired
    public SettingUserService(
        SettingUserRepository settingUserRepository,
        SecurityService securityService
    ) {
        this.settingUserRepository = settingUserRepository;
        this.securityService = securityService;
    }

    public void generateSetting(User user) {
        settingUserRepository.save(new SettingUser(user));
    }

    public SettingAPI mySetting() {
        User user = securityService.getCurrentUser();
        return new SettingAPI(pushWork(user));
    }

    public boolean pushWork(User user) {
        return settingUserRepository.findByUser(user).isPushWork();
    }

    public void pushSet(User user, boolean value) {
        SettingUser setting = settingUserRepository.findByUser(user);
        setting.setPushWork(value);
        settingUserRepository.save(setting);
    }
}
