package com.memastick.backmem.setting.service;

import com.memastick.backmem.memetick.api.MemetickPreviewAPI;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.mapper.MemetickMapper;
import com.memastick.backmem.memetick.repository.MemetickRepository;
import com.memastick.backmem.security.component.OauthData;
import com.memastick.backmem.setting.entity.SettingFollower;
import com.memastick.backmem.setting.repository.SettingFollowerRepository;
import com.memastick.backmem.user.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SettingFollowerService {

    private final OauthData oauthData;
    private final SettingFollowerRepository settingFollowerRepository;
    private final MemetickMapper memetickMapper;
    private final MemetickRepository memetickRepository;

    public void trigger(UUID memetickId) {
        User follower = oauthData.getCurrentUser();
        Memetick memetick = memetickRepository.tryFindById(memetickId);

        if (follower.getMemetick().getId().equals(memetick.getId())) return;

        Optional<SettingFollower> optional = settingFollowerRepository.findByMemetickAndFollower(memetick, follower);

        if (optional.isEmpty()) {
            settingFollowerRepository.save(new SettingFollower(memetick, follower));
        } else {
            settingFollowerRepository.delete(optional.get());
        }
    }

    public boolean follow(Memetick memetick) {
        User follower = oauthData.getCurrentUser();
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
        return this.findMemeticks(oauthData.getCurrentUser())
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
