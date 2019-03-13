package com.memastick.backmem.memes.service;

import com.memastick.backmem.main.util.MathUtil;
import com.memastick.backmem.memes.constant.MemeType;
import com.memastick.backmem.memes.dto.MemeLikeStateDTO;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memes.entity.MemeLike;
import com.memastick.backmem.memes.repository.MemeLikeRepository;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.service.MemetickService;
import com.memastick.backmem.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class MemeLikeService {

    private static final int MAX_CHROMOSOME = 10;

    private final MemetickService memetickService;
    private final MemeLikeRepository memeLikeRepository;
    private final SecurityService securityService;
    private final MemeService memeService;

    @Autowired
    public MemeLikeService(
        MemeLikeRepository memeLikeRepository,
        SecurityService securityService,
        MemetickService memetickService,
        MemeService memeService
    ) {
        this.memeLikeRepository = memeLikeRepository;
        this.securityService = securityService;
        this.memetickService = memetickService;
        this.memeService = memeService;
    }

    public MemeLikeStateDTO readStateByMeme(Meme meme) {
        MemeLike memeLike = findByMemeForCurrentUser(meme);

        return new MemeLikeStateDTO(
            memeLikeRepository.countByMemeIdAndIsLikeTrue(meme.getId()).orElse(0L),
            memeLike.isLike(),
            memeLike.getChromosome()
        );
    }

    public void likeTrigger(UUID id) {
        Meme meme = memeService.findById(id);

        if (MemeType.DEATH.equals(meme.getType())) return;

        MemeLike memeLike = findByMemeForCurrentUser(meme);
        memeLike.setLike(!memeLike.isLike());

        memeLikeRepository.save(memeLike);

        int randDna = MathUtil.rand(0, 100);

        memetickService.addDna(
            memeLike.getMeme().getMemetick(),
            memeLike.isLike() ? randDna : randDna * -1
        );
    }

    public void chromosomeTrigger(UUID id, int count) {
        Meme meme = memeService.findById(id);
        chromosomeTrigger(meme, count);
    }

    public void chromosomeTrigger(Meme meme, int count) {
        MemeLike memeLike = findByMemeForCurrentUser(meme);

        if (MemeType.DEATH.equals(meme.getType())) return;
        if (memeLike.getChromosome() >= MAX_CHROMOSOME) return;

        int chromosome = Math.min(memeLike.getChromosome() + count, MAX_CHROMOSOME);
        int allChromosome = meme.getChromosomes() + (chromosome - memeLike.getChromosome());

        memeLike.setChromosome(chromosome);
        meme.setChromosomes(allChromosome);

        memeLikeRepository.save(memeLike);
        memetickService.addDna(memeLike.getMeme().getMemetick(), MathUtil.rand(0, chromosome));
    }

    public List<Meme> findLikeMemesByMemetick(Memetick memetick) {
        return memeLikeRepository
            .findByMemetickAndIsLikeTrue(memetick)
            .stream()
            .map(MemeLike::getMeme)
            .sorted(Comparator.comparing(Meme::getCreating))
            .collect(Collectors.toList());
    }

    private MemeLike findByMemeForCurrentUser(Meme meme) {
        Memetick memetick = securityService.getCurrentMemetick();

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
