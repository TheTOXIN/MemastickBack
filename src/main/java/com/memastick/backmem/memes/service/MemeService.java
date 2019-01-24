package com.memastick.backmem.memes.service;

import com.memastick.backmem.memes.api.MemeCreateAPI;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memes.repository.MemeRepository;
import com.memastick.backmem.person.entity.Memetick;
import com.memastick.backmem.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class MemeService {

    private final SecurityService securityService;
    private final MemeRepository memeRepository;

    @Autowired
    public MemeService(
        SecurityService securityService,
        MemeRepository memeRepository
    ) {
        this.securityService = securityService;
        this.memeRepository = memeRepository;
    }

    public void create(MemeCreateAPI request) {
        Memetick memetick = securityService.getCurrentUser().getMemetick();

        Meme meme = makeMeme(request.getFireId(), memetick);

        memeRepository.save(meme);
    }

    public Meme makeMeme(UUID fireId, Memetick memetick) {
        return new Meme(
            fireId,
            memetick,
            ZonedDateTime.now()
        );
    }

    public List<UUID> readAll(Pageable pageable) {
        return memeRepository.findAll(pageable)
            .stream()
            .map(Meme::getFireId)
            .collect(Collectors.toList());
    }
}
