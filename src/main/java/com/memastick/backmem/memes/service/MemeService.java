package com.memastick.backmem.memes.service;

import com.memastick.backmem.evolution.entity.EvolveMeme;
import com.memastick.backmem.evolution.repository.EvolveMemeRepository;
import com.memastick.backmem.main.constant.LinkConstant;
import com.memastick.backmem.memecoin.service.MemeCoinService;
import com.memastick.backmem.memes.api.MemeAPI;
import com.memastick.backmem.memes.api.MemeImgAPI;
import com.memastick.backmem.memes.api.MemePageAPI;
import com.memastick.backmem.memes.constant.MemeType;
import com.memastick.backmem.memes.dto.MemeReadDTO;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memes.mapper.MemeMapper;
import com.memastick.backmem.memes.repository.MemeRepository;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.repository.MemetickRepository;
import com.memastick.backmem.security.component.OauthData;
import com.memastick.backmem.shop.constant.PriceConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MemeService {

    private final OauthData oauthData;
    private final MemeRepository memeRepository;
    private final MemeMapper memeMapper;
    private final MemeLikeService memeLikeService;
    private final MemePoolService memePoolService;
    private final MemeCoinService memeCoinService;
    private final MemetickRepository memetickRepository;
    private final EvolveMemeRepository evolveMemeRepository;

    @Autowired
    public MemeService(
        OauthData oauthData,
        MemeRepository memeRepository,
        @Lazy MemeMapper memeMapper,
        @Lazy MemeLikeService memeLikeService,
        @Lazy MemePoolService memePoolService,
        MemeCoinService memeCoinService,
        MemetickRepository memetickRepository,
        EvolveMemeRepository evolveMemeRepository
    ) {
        this.oauthData = oauthData;
        this.memeRepository = memeRepository;
        this.memeMapper = memeMapper;
        this.memeLikeService = memeLikeService;
        this.memePoolService = memePoolService;
        this.memeCoinService = memeCoinService;
        this.memetickRepository = memetickRepository;
        this.evolveMemeRepository = evolveMemeRepository;
    }

    public List<MemeAPI> read(MemeReadDTO readDTO, Pageable pageable) {
        return reader(readDTO, pageable)
            .stream()
            .map(memeMapper::toMemeAPI)
            .collect(Collectors.toList());
    }

    public List<MemePageAPI> pages(MemeReadDTO readDTO, Pageable pageable) {
        return reader(readDTO, pageable)
            .stream()
            .map(memeMapper::toPageAPI)
            .collect(Collectors.toList());
    }

    public MemePageAPI page(UUID memeId) {
        Meme meme = memeRepository.tryFindById(memeId);
        return memeMapper.toPageAPI(meme);
    }

    public MemeImgAPI readImg(UUID memeId) {
        Meme meme = memeRepository.tryFindById(memeId);
        return new MemeImgAPI(meme.getUrl());
    }

    @Transactional
    public void moveIndex(Meme meme) {
        long newIndex = meme.getIndividuation() + 1;
        long oldIndex = meme.getIndividuation();

        Meme prevMeme = memeRepository.findByEvolutionAndPopulationAndIndividuation(
            meme.getEvolution(),
            meme.getPopulation(),
            newIndex
        ).orElse(null);

        if (prevMeme == null) return;

        meme.setIndividuation(newIndex);
        prevMeme.setIndividuation(oldIndex);

        memeRepository.save(meme);
        memeRepository.save(prevMeme);
    }

    @Transactional(readOnly = true)
    public List<Meme> reader(MemeReadDTO readDTO, Pageable pageable) {
        List<Meme> memes = new ArrayList<>();

        Memetick memetick = oauthData.getCurrentMemetick();

        switch (readDTO.getFilter()) {
            case EVLV: memes = memeRepository.findByType(MemeType.EVLV, pageable); break;
            case SLCT: memes = memeRepository.findByType(MemeType.SLCT, pageable); break;
            case INDV: memes = memeRepository.findByType(MemeType.INDV, pageable); break;
            case DEAD: memes = memeRepository.findByTypeAndMemetick(MemeType.DEAD, memetick, pageable); break;
            case SELF: memes = memeRepository.findByMemetick(memetick, pageable); break;
            case BATL: memes = memeRepository.findByTypeAndMemetick(MemeType.INDV, memetick, pageable); break;
            case USER: memes = memeRepository.findByMemetick(memetickRepository.tryFindById(readDTO.getMemetickId()), pageable); break;
            case LIKE: memes = memeLikeService.findMemesByLikeFilter(memetick, pageable); break;
            case POOL: memes = memePoolService.generate(readDTO.getStep(), pageable); break;
        }

        return memes;
    }

    @Transactional
    public void resurrect(UUID memeId) {
        Memetick memetick = oauthData.getCurrentMemetick();
        Meme meme = memeRepository.tryFindById(memeId);

        if (!MemeType.DEAD.equals(meme.getType())) return;
        memeCoinService.transaction(memetick, PriceConst.RESSURECTION.getPrice());

        meme.setType(MemeType.INDV);
        memeRepository.save(meme);
    }

    @Transactional
    public void ban(UUID memeId) {
        Meme meme = memeRepository.tryFindById(memeId);

        meme.setType(MemeType.BAAN);
        meme.setUrl(LinkConstant.LINK_MEME_BAN);
        meme.setText("МЕМ ЗАБАНЕН");
        meme.setChromosomes(0);

        memeRepository.save(meme);

        EvolveMeme evolveMeme = evolveMemeRepository.findByMeme(meme);

        evolveMeme.setStep(null);

        evolveMemeRepository.save(evolveMeme);
    }
}

