package com.memastick.backmem.memes.service;

import com.memastick.backmem.errors.exception.EntityNotFoundException;
import com.memastick.backmem.errors.exception.MemeTokenExcpetion;
import com.memastick.backmem.main.util.MathUtil;
import com.memastick.backmem.memes.api.MemeCreateAPI;
import com.memastick.backmem.memes.api.MemeReadAPI;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memes.repository.MemeRepository;
import com.memastick.backmem.person.entity.Memetick;
import com.memastick.backmem.person.repository.MemetickRepository;
import com.memastick.backmem.person.service.MemetickService;
import com.memastick.backmem.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class MemeService {

    private final SecurityService securityService;
    private final MemeRepository memeRepository;
    private final MemetickRepository memetickRepository;
    private final MemetickService memetickService;

    @Autowired
    public MemeService(
        SecurityService securityService,
        MemeRepository memeRepository,
        MemetickRepository memetickRepository,
        MemetickService memetickService
    ) {
        this.securityService = securityService;
        this.memeRepository = memeRepository;
        this.memetickRepository = memetickRepository;
        this.memetickService = memetickService;
    }

    public void create(MemeCreateAPI request) {
        Memetick memetick = securityService.getCurrentMemetick();

        if (memetick.getMemeCreated().plusDays(1).isAfter(ZonedDateTime.now()))
            throw new MemeTokenExcpetion();

        Meme meme = makeMeme(request.getFireId(), memetick);
        memeRepository.save(meme);

        memetick.setMemeCreated(ZonedDateTime.now());
        memetickRepository.save(memetick);

        memetickService.addDna(memetick, MathUtil.rand(500, 1000));
    }

    public Meme makeMeme(UUID fireId, Memetick memetick) {
        return new Meme(
            fireId,
            memetick,
            ZonedDateTime.now()
        );
    }

    public List<MemeReadAPI> readAll(Pageable pageable) {
        return memeRepository.findAll(pageable)
            .stream()
            .map(MemeService::mapToReadAPI)
            .collect(Collectors.toList());
    }

    public Meme findById(UUID id) {
        Optional<Meme> byId = memeRepository.findById(id);
        if (byId.isEmpty()) throw new EntityNotFoundException(Meme.class, "id");
        return byId.get();
    }

    private static MemeReadAPI mapToReadAPI(Meme meme) {
        return new MemeReadAPI(
            meme.getId(),
            meme.getFireId(),
            meme.getMemetick().getId()
        );
    }
}