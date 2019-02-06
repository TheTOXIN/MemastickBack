package com.memastick.backmem.memes.service;

import com.memastick.backmem.main.util.MathUtil;
import com.memastick.backmem.memes.dto.MemeLikeStateDTO;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memes.entity.MemeLike;
import com.memastick.backmem.memes.repository.MemeLikeRepository;
import com.memastick.backmem.memes.repository.MemeRepository;
import com.memastick.backmem.person.entity.Memetick;
import com.memastick.backmem.person.service.MemetickService;
import com.memastick.backmem.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;


@Service
public class MemeLikeService {

    private static final int MAX_CHROMOSOME = 30;

    private final MemetickService memetickService;
    private final MemeLikeRepository memeLikeRepository;
    private final SecurityService securityService;
    private final MemeRepository memeRepository;
    private final MemeService memeService;

    @Autowired
    public MemeLikeService(
        MemeLikeRepository memeLikeRepository,
        SecurityService securityService,
        MemeRepository memeRepository,
        MemetickService memetickService,
        MemeService memeService
    ) {
        this.memeLikeRepository = memeLikeRepository;
        this.securityService = securityService;
        this.memeRepository = memeRepository;
        this.memetickService = memetickService;
        this.memeService = memeService;
    }

    @Transactional
    public MemeLikeStateDTO readStateById(UUID id) {
        MemeLike memeLike = findByIdForCurrentUser(id);

        long countLikes = memeLikeRepository.countByMemeIdAndIsLikeTrue(id).orElse(0L);
        long countChromosomes = memeLikeRepository.sumChromosomeByMemeId(id).orElse(0L);

        return new MemeLikeStateDTO(
            (int) countLikes,
            (int) countChromosomes,
            memeLike.isLike(),
            memeLike.getChromosome()
        );
    }

    public void likeTrigger(UUID id) {
        MemeLike memeLike = findByIdForCurrentUser(id);

        memeLike.setLike(!memeLike.isLike());

        memeLikeRepository.save(memeLike);

        int randDna = MathUtil.rand(10, 100);
        memetickService.addDna(
            memeLike.getMeme().getMemetick(),
            memeLike.isLike() ? randDna : randDna * -1
        );
    }

    public void chromosomeTrigger(UUID id, int count) {
        MemeLike memeLike = findByIdForCurrentUser(id);

        if (memeLike.getChromosome() == MAX_CHROMOSOME) return;

        int chromosome = Math.min(memeLike.getChromosome() + count, MAX_CHROMOSOME);
        memeLike.setChromosome(chromosome);

        memeLikeRepository.save(memeLike);

        memetickService.addDna(memeLike.getMeme().getMemetick(), MathUtil.rand(0, chromosome));
    }

    private MemeLike findByIdForCurrentUser(UUID id) {
        Memetick memetick = securityService.getCurrentMemetick();
        Meme meme = memeService.findById(id);

        Optional<MemeLike> byMemeAndMemetick = memeLikeRepository.findByMemeAndMemetick(meme, memetick);

        if (byMemeAndMemetick.isEmpty()) return generateMemeLike(meme, memetick);

        return byMemeAndMemetick.get();
    }

    private MemeLike generateMemeLike(Meme meme, Memetick memetick) {
        MemeLike memeLike = new MemeLike();

        memeLike.setMeme(meme);
        memeLike.setMemetick(memetick);

        return memeLikeRepository.save(memeLike);
    }
}
