package com.memastick.backmem.memes.service;

import com.memastick.backmem.memes.api.MemeLikeAPI;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memes.entity.MemeLike;
import com.memastick.backmem.memes.repository.MemeLikeRepository;
import com.memastick.backmem.memes.repository.MemeRepository;
import com.memastick.backmem.person.entity.Memetick;
import com.memastick.backmem.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public MemeLikeAPI readByFireId(UUID fireId) {
        Memetick memetick = securityService.getCurrentUser().getMemetick();
        Meme meme = memeRepository.findByFireId(fireId);

        MemeLike memeLike = memeLikeRepository.findByMemeAndMemetick(meme, memetick);

        long countLikes = memeLikeRepository.countByMemeAndLikeTrue(meme);
        long countChromosomes = memeLikeRepository.sumChromosomeByMeme(meme);

        return new MemeLikeAPI(
            (int) countLikes,
            (int) countChromosomes,
            memeLike != null && memeLike.isLike(),
            memeLike != null ? memeLike.getChromosome() : 0
        );
    }

    public void likeTrigger(UUID fireId) {
        MemeLike memeLike = findByFireIdForCurrentUser(fireId);

        memeLike.setLike(!memeLike.isLike());

        memeLikeRepository.save(memeLike);
    }

    public void chromosomeTrigger(UUID fireId, int count) {
        MemeLike memeLike = findByFireIdForCurrentUser(fireId);

        if (count > MAX_CHROMOSOME || memeLike.getChromosome() == MAX_CHROMOSOME) return;

        memeLike.setChromosome(Math.min(memeLike.getChromosome() + count, MAX_CHROMOSOME));

        memeLikeRepository.save(memeLike);
    }

    private MemeLike findByFireIdForCurrentUser(UUID fireId) {
        Memetick memetick = securityService.getCurrentUser().getMemetick();
        Meme meme = memeRepository.findByFireId(fireId);

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
}
