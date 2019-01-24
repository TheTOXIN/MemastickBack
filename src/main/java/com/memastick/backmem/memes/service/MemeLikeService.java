package com.memastick.backmem.memes.service;

import com.memastick.backmem.errors.exception.EntityNotFoundException;
import com.memastick.backmem.memes.api.MemeLikeStateAPI;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memes.entity.MemeLike;
import com.memastick.backmem.memes.repository.MemeLikeRepository;
import com.memastick.backmem.memes.repository.MemeRepository;
import com.memastick.backmem.person.entity.Memetick;
import com.memastick.backmem.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;


@Service
public class MemeLikeService {

    private static final int MAX_CHROMOSOME = 30;

    private final MemeLikeRepository memeLikeRepository;
    private final SecurityService securityService;
    private final MemeRepository memeRepository;

    @Autowired
    public MemeLikeService(
        MemeLikeRepository memeLikeRepository,
        SecurityService securityService,
        MemeRepository memeRepository
    ) {
        this.memeLikeRepository = memeLikeRepository;
        this.securityService = securityService;
        this.memeRepository = memeRepository;
    }

    @Transactional
    public MemeLikeStateAPI readStateById(UUID id) {
        Memetick memetick = securityService.getCurrentUser().getMemetick();
        Meme meme = findById(id);

        MemeLike memeLike = memeLikeRepository.findByMemeAndMemetick(meme, memetick);

        long countLikes = memeLikeRepository.countByMemeAndLikeTrue(meme);
        long countChromosomes = memeLikeRepository.sumChromosomeByMeme(meme);

        return new MemeLikeStateAPI(
            (int) countLikes,
            (int) countChromosomes,
            memeLike != null && memeLike.isLike(),
            memeLike != null ? memeLike.getChromosome() : 0
        );
    }

    public void likeTrigger(UUID id) {
        MemeLike memeLike = findByIdForCurrentUser(id);

        memeLike.setLike(!memeLike.isLike());

        memeLikeRepository.save(memeLike);
    }

    public void chromosomeTrigger(UUID id, int count) {
        MemeLike memeLike = findByIdForCurrentUser(id);

        if (count > MAX_CHROMOSOME || memeLike.getChromosome() == MAX_CHROMOSOME) return;

        memeLike.setChromosome(Math.min(memeLike.getChromosome() + count, MAX_CHROMOSOME));

        memeLikeRepository.save(memeLike);
    }

    private MemeLike findByIdForCurrentUser(UUID id) {
        Memetick memetick = securityService.getCurrentUser().getMemetick();
        Meme meme = findById(id);

        MemeLike memeLike = memeLikeRepository.findByMemeAndMemetick(meme, memetick);

        if (memeLike == null) memeLike = generateMemeLike(meme, memetick);

        return memeLike;
    }

    private MemeLike generateMemeLike(Meme meme, Memetick memetick) {
        MemeLike memeLike = new MemeLike();

        memeLike.setMeme(meme);
        memeLike.setMemetick(memetick);

        return memeLikeRepository.save(memeLike);
    }

    private Meme findById(UUID id) {
        Optional<Meme> byId = memeRepository.findById(id);
        if (byId.isEmpty()) throw new EntityNotFoundException(Meme.class, "id");
        return byId.get();
    }
}
