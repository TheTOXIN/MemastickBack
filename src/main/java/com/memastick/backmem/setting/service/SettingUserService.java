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
        settingUserRepository.save(
            new SettingUser(user)
        );
    }

    public SettingAPI mySetting() {
        User user = securityService.getCurrentUser();
        return new SettingAPI(pushWork(user));
    }

    public void pushTrigger() {
        User user = securityService.getCurrentUser();
        SettingUser setting = settingUserRepository.findByUser(user);

        if (setting.getPushWork() == null) setting.setPushWork(false);
        setting.setPushWork(!setting.getPushWork());

        settingUserRepository.save(setting);
    }

    public boolean pushWork(User user) {
        SettingUser setting = settingUserRepository.findByUser(user);
        return Boolean.TRUE.equals(setting.getPushWork());
    }

    public boolean pushAsk(User user) {
        SettingUser setting = settingUserRepository.findByUser(user);
        return setting.getPushWork() == null;
    }
}
