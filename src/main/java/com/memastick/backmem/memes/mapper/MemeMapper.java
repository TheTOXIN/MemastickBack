package com.memastick.backmem.memes.mapper;

import com.memastick.backmem.evolution.constant.EvolveStep;
import com.memastick.backmem.evolution.repository.EvolveMemeRepository;
import com.memastick.backmem.memes.dto.MemeDTO;
import com.memastick.backmem.memes.entity.Meme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemeMapper {

    private final EvolveMemeRepository evolveMemeRepository;

    @Autowired
    public MemeMapper(
        EvolveMemeRepository evolveMemeRepository
    ) {
        this.evolveMemeRepository = evolveMemeRepository;
    }

    public MemeDTO toMemeDTO(Meme meme) {
        EvolveStep step = evolveMemeRepository.findStepByMeme(meme);
        return new MemeDTO(
            meme.getId(),
            meme.getUrl(),
            meme.getType(),
            step
        );
    }
}
