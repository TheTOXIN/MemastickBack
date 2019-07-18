package com.memastick.backmem.memes.mapper;

import com.memastick.backmem.evolution.constant.EvolveStep;
import com.memastick.backmem.evolution.repository.EvolveMemeRepository;
import com.memastick.backmem.memes.api.MemePageAPI;
import com.memastick.backmem.memes.dto.MemeAPI;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memes.service.MemeLikeService;
import com.memastick.backmem.memetick.mapper.MemetickMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemeMapper {

    private final MemeLikeService memeLikeService;
    private final MemetickMapper memetickMapper;
    private final EvolveMemeRepository evolveMemeRepository;

    @Autowired
    public MemeMapper(
        MemeLikeService memeLikeService,
        MemetickMapper memetickMapper,
        EvolveMemeRepository evolveMemeRepository
    ) {
        this.memeLikeService = memeLikeService;
        this.memetickMapper = memetickMapper;
        this.evolveMemeRepository = evolveMemeRepository;
    }

    public MemePageAPI toPageAPI(Meme meme) {
        return new MemePageAPI(
            this.toMemeAPI(meme),
            memeLikeService.readStateByMeme(meme),
            memetickMapper.toPreviewDTO(meme.getMemetick())
        );
    }

    public MemeAPI toMemeAPI(Meme meme) {
        return new MemeAPI(
            meme.getId(),
            meme.getUrl(),
            meme.getText(),
            meme.getType(),
            meme.getChromosomes(),
            meme.getIndividuation(),
            (EvolveStep) evolveMemeRepository.findStepByMeme(meme)
        );
    }
}
