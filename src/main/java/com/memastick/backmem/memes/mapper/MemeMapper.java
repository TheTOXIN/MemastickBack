package com.memastick.backmem.memes.mapper;

import com.memastick.backmem.evolution.service.EvolveMemeService;
import com.memastick.backmem.main.dto.EPI;
import com.memastick.backmem.memes.api.MemePageAPI;
import com.memastick.backmem.memes.dto.MemeAPI;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memes.service.MemeLikeService;
import com.memastick.backmem.memetick.mapper.MemetickMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MemeMapper {

    private final MemeLikeService memeLikeService;
    private final MemetickMapper memetickMapper;
    private final EvolveMemeService evolveMemeService;

    public MemePageAPI toPageAPI(Meme meme) {
        return new MemePageAPI(
            this.toMemeAPI(meme),
            memeLikeService.readStateByMeme(meme),
            memetickMapper.toPreviewDTO(meme.getMemetick()),
            evolveMemeService.computeStep(meme)
        );
    }

    public MemeAPI toMemeAPI(Meme meme) {
        return new MemeAPI(
            meme.getId(),
            meme.getUrl(),
            meme.getText(),
            meme.getType(),
            meme.getLikes(),
            meme.getChromosomes(),
            new EPI(
                meme.getEvolution(),
                meme.getPopulation(),
                meme.getIndividuation()
            )
        );
    }
}
