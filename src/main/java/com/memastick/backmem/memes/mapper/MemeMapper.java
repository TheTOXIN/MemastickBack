package com.memastick.backmem.memes.mapper;

import com.memastick.backmem.evolution.constant.EvolveStep;
import com.memastick.backmem.evolution.repository.EvolveMemeRepository;
import com.memastick.backmem.memes.api.MemePageAPI;
import com.memastick.backmem.memes.dto.MemeAPI;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memes.service.MemeLikeService;
import com.memastick.backmem.memetick.mapper.MemetickMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class MemeMapper {

    private final MemeLikeService memeLikeService;
    private final MemetickMapper memetickMapper;
    private final EvolveMemeRepository evolveMemeRepository;

    @Autowired
    public MemeMapper(
        EvolveMemeRepository evolveMemeRepository,
        @Lazy MemeLikeService memeLikeService,
        MemetickMapper memetickMapper
    ) {
        this.evolveMemeRepository = evolveMemeRepository;
        this.memeLikeService = memeLikeService;
        this.memetickMapper = memetickMapper;
    }

    public MemePageAPI toPageAPI(Meme meme) {
        return new MemePageAPI(
            this.toMemeAPI(meme),
            memeLikeService.readStateByMeme(meme),
            memetickMapper.toPreviewDTO(meme.getMemetick())
        );
    }

    public MemeAPI toMemeAPI(Meme meme) {
        EvolveStep step = evolveMemeRepository.findStepByMeme(meme);
        return new MemeAPI(
            meme.getId(),
            meme.getUrl(),
            meme.getType(),
            step
        );
    }
}
