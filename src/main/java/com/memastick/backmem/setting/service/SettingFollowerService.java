package com.memastick.backmem.setting.service;

import com.memastick.backmem.memetick.api.MemetickPreviewAPI;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.mapper.MemetickMapper;
import com.memastick.backmem.memetick.service.MemetickService;
import com.memastick.backmem.security.service.SecurityService;
import com.memastick.backmem.setting.entity.SettingFollower;
import com.memastick.backmem.setting.repository.SettingFollowerRepository;
import com.memastick.backmem.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SettingFollowerService {

    private final MemetickService memetickService;
    private final SecurityService securityService;
    private final SettingFollowerRepository settingFollowerRepository;
    private final MemetickMapper memetickMapper;

    @Autowired
    public SettingFollowerService(
        @Lazy MemetickService memetickService,
        SecurityService securityService,
        SettingFollowerRepository settingFollowerRepository,
        MemetickMapper memetickMapper
    ) {
        this.memetickService = memetickService;
        this.securityService = securityService;
        this.settingFollowerRepository = settingFollowerRepository;
        this.memetickMapper = memetickMapper;
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

    public List<User> findFollowers(Memetick memetick) {
        return settingFollowerRepository.findAllByMemetick(memetick)
            .stream()
            .map(SettingFollower::getFollower)
            .collect(Collectors.toList());
    }

    public List<MemetickPreviewAPI> following() {
        return this.findMemeticks(securityService.getCurrentUser())
            .stream()
            .map(memetickMapper::toPreviewDTO)
            .collect(Collectors.toList());
    }

    private List<Memetick> findMemeticks(User follower) {
        return settingFollowerRepository.findAllByFollower(follower)
            .stream()
            .map(SettingFollower::getMemetick)
            .collect(Collectors.toList());
    }
}
