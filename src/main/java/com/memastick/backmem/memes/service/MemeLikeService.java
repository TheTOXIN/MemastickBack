package com.memastick.backmem.memes.service;

import com.memastick.backmem.main.util.MathUtil;
import com.memastick.backmem.memes.constant.MemeType;
import com.memastick.backmem.memes.dto.MemeLikeStateDTO;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memes.entity.MemeLike;
import com.memastick.backmem.memes.repository.MemeLikeRepository;
import com.memastick.backmem.memes.repository.MemeRepository;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.service.MemetickService;
import com.memastick.backmem.security.component.OauthData;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.memastick.backmem.main.constant.DnaCount.CHROMOSOME;
import static com.memastick.backmem.main.constant.ValidConstant.MAX_CHROMOSOME;

@Service
@AllArgsConstructor
public class MemeLikeService {

    private final MemetickService memetickService;
    private final MemeLikeRepository memeLikeRepository;
    private final OauthData oauthData;

    private final MemeRepository memeRepository;

    public MemeLikeStateDTO readStateByMeme(Meme meme) {
        Memetick memetick = oauthData.getCurrentMemetick();
        MemeLike memeLike = memeLikeRepository.findByMemeAndMemetick(meme, memetick).orElse(new MemeLike());

        return new MemeLikeStateDTO(
            memeLike.isLike(),
            memeLike.getChromosome()
        );
    }

    public void likeTrigger(UUID id) {
        Meme meme = memeRepository.tryFindById(id);
        MemeLike memeLike = findByMemeForCurrentUser(meme);

        memeLike.setLike(!memeLike.isLike());
        meme.setLikes(meme.getLikes() + (memeLike.isLike() ? 1 : -1));

        if (memeLike.isLike()) memeLike.setLikeTime(LocalDateTime.now());

        memeLikeRepository.save(memeLike);
    }

    public void chromosomeTrigger(UUID memeId, int count) {
        Meme meme = memeRepository.tryFindById(memeId);
        MemeLike memeLike = findByMemeForCurrentUser(meme);

        if (MemeType.DEAD.equals(meme.getType())) return;
        if (memeLike.getChromosome() >= MAX_CHROMOSOME) return;

        if (memeLike.getChromosome() == 0) memetickService.addDna(memeLike.getMemetick(), MathUtil.rand(0, CHROMOSOME));

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
