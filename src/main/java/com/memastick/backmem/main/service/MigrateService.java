package com.memastick.backmem.main.service;

import com.memastick.backmem.memetick.entity.MemetickAvatar;
import com.memastick.backmem.memetick.repository.MemetickAvatarRepository;
import com.memastick.backmem.memetick.service.MemetickAvatarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MigrateService {

    private final MemetickAvatarRepository memetickAvatarRepository;
    private final MemetickAvatarService memetickAvatarService;

    @Autowired
    public MigrateService(
        MemetickAvatarRepository memetickAvatarRepository,
        MemetickAvatarService memetickAvatarService
    ) {
        this.memetickAvatarRepository = memetickAvatarRepository;
        this.memetickAvatarService = memetickAvatarService;
    }

    public void migrate() {
        var avatars = StreamSupport
            .stream(memetickAvatarRepository.findAll().spliterator(), false)
            .filter(a -> a.getAvatar() == null)
            .collect(Collectors.toList());

        var memeticks = avatars
            .stream()
            .map(MemetickAvatar::getMemetick)
            .collect(Collectors.toList());

        memetickAvatarRepository.deleteAll(avatars);
        memeticks.forEach(memetickAvatarService::generateAvatar);
    }
}
