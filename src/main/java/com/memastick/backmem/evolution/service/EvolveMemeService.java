package com.memastick.backmem.evolution.service;

import com.memastick.backmem.evolution.api.EvolveMemeAPI;
import com.memastick.backmem.evolution.constant.EvolveStep;
import com.memastick.backmem.evolution.entity.EvolveMeme;
import com.memastick.backmem.evolution.repository.EvolveMemeRepository;
import com.memastick.backmem.main.constant.GlobalConstant;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memes.repository.MemeRepository;
import com.memastick.backmem.memes.service.MemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.UUID;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class EvolveMemeService {

    private final MemeRepository memeRepository;
    private final EvolveMemeRepository evolveMemeRepository;
    private final MemeService memeService;

    @Autowired
    public EvolveMemeService(
        EvolveMemeRepository evolveMemeRepository,
        MemeRepository memeRepository,
        MemeService memeService
    ) {
        this.evolveMemeRepository = evolveMemeRepository;
        this.memeRepository = memeRepository;
        this.memeService = memeService;
    }

    public void startEvolve(Meme meme) {
        evolveMemeRepository.save(new EvolveMeme(
            meme,
            EvolveStep.BIRTH,
            evolveDay()
        ));
    }

    public float computeChance(EvolveMeme evolveMeme) {
        Integer max = memeRepository.maxChromosomes();
        Integer min = memeRepository.minChromosomes();

        float onePercent = (max - min) / 100;

        return evolveMeme.getChanceSurvive() * onePercent;
    }

    public void nextStep(EvolveMeme evolveMeme) {
        if (evolveMeme.getStep() == null) return;

        EvolveStep nowStep = evolveMeme.getStep();
        EvolveStep nextStep = EvolveStep.find(nowStep.getStep() + 1);

        evolveMeme.setStep(nextStep);
    }

    public long evolveDay() {
        return DAYS.between(
            GlobalConstant.START_EVOLVE,
            LocalDate.now(ZoneOffset.UTC)
        );
    }

    public EvolveMemeAPI readByMeme(UUID memeId) {
        Meme meme = memeService.findById(memeId);
        EvolveMeme evolveMeme = evolveMemeRepository.findByMeme(meme);

        return new EvolveMemeAPI(
            evolveMeme.getStep(),
            evolveMeme.getPopulation(),
            evolveMeme.getChanceSurvive()
        );
    }
}
