package com.memastick.backmem.setting.service;

import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.service.MemetickService;
import com.memastick.backmem.security.service.SecurityService;
import com.memastick.backmem.setting.entity.SettingFollower;
import com.memastick.backmem.setting.repository.SettingFollowerRepository;
import com.memastick.backmem.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class SettingFollowerService {

    private final MemetickService memetickService;
    private final SecurityService securityService;
    private final SettingFollowerRepository settingFollowerRepository;

    @Autowired
    public SettingFollowerService(
        MemetickService memetickService,
        SecurityService securityService,
        SettingFollowerRepository settingFollowerRepository
    ) {
        this.memetickService = memetickService;
        this.securityService = securityService;
        this.settingFollowerRepository = settingFollowerRepository;
    }

    public void trigger(UUID memetickId) {
        User follower = securityService.getCurrentUser();
        Memetick memetick = memetickService.findById(memetickId);

        if (follower.getMemetick().equals(memetick)) return;

        Optional<SettingFollower> optional = settingFollowerRepository.findByMemetickAndFollower(memetick, follower);

        if (optional.isEmpty()) {
            settingFollowerRepository.save(new SettingFollower(memetick, follower));
        } else {
            settingFollowerRepository.delete(optional.get());
        }
    }

    public boolean follow(Memetick memetick) {
        User follower = securityService.getCurrentUser();
        Optional<SettingFollower> optional = settingFollowerRepository.findByMemetickAndFollower(memetick, follower);

        return optional.isPresent();
    }
}
