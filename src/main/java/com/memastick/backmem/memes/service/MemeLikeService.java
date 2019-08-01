package com.memastick.backmem.memes.service;

import com.memastick.backmem.main.util.MathUtil;
import com.memastick.backmem.memes.constant.MemeType;
import com.memastick.backmem.memes.dto.MemeLikeStateDTO;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memes.entity.MemeLike;
import com.memastick.backmem.memes.repository.MemeLikeRepository;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.service.MemetickService;
import com.memastick.backmem.security.component.OauthData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.memastick.backmem.main.constant.GlobalConstant.MAX_CHROMOSOME;


@Service
public class MemeLikeService {

    private final MemetickService memetickService;
    private final MemeLikeRepository memeLikeRepository;
    private final OauthData oauthData;
    private final MemeService memeService;

    @Autowired
    public MemeLikeService(
        MemeLikeRepository memeLikeRepository,
        OauthData oauthData,
        MemetickService memetickService,
        MemeService memeService
    ) {
        this.memeLikeRepository = memeLikeRepository;
        this.oauthData = oauthData;
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

        MemeLike memeLike = findByMemeForCurrentUser(meme);
        memeLike.setLike(!memeLike.isLike());

        if (memeLike.isLike()) memeLike.setLikeTime(LocalDateTime.now());

        memeLikeRepository.save(memeLike);
    }

    public void chromosomeTrigger(UUID id, int count) {
        Meme meme = memeService.findById(id);
        chromosomeTrigger(meme, count);
    }

    public void chromosomeTrigger(Meme meme, int count) {
        MemeLike memeLike = findByMemeForCurrentUser(meme);

        if (MemeType.DEAD.equals(meme.getType())) return;
        if (memeLike.getChromosome() >= MAX_CHROMOSOME) return;

        if (memeLike.getChromosome() == 0) memetickService.addDna(memeLike.getMemetick(), MathUtil.rand(1, 10));

        int chromosome = Math.min(memeLike.getChromosome() + count, MAX_CHROMOSOME);
        int allChromosome = meme.getChromosomes() + (chromosome - memeLike.getChromosome());

        memeLike.setChromosome(chromosome);
        meme.setChromosomes(allChromosome);

        memeLikeRepository.save(memeLike);
    }

    public List<Meme> findMemesByLikeFilter(Memetick memetick, Pageable pageable) {
        PageRequest likePageable = PageRequest.of(
            pageable.getPageNumber(),
            pageable.getPageSize(),
            Sort.by(Sort.Order.desc(("likeTime")))
        );

        return memeLikeRepository
            .findByMemetickAndIsLikeTrue(memetick, likePageable)
            .stream()
            .map(MemeLike::getMeme)
            .collect(Collectors.toList());
    }

    private MemeLike findByMemeForCurrentUser(Meme meme) {
        Memetick memetick = oauthData.getCurrentMemetick();

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
