package com.memastick.backmem.evolution.service;

import com.memastick.backmem.evolution.constant.EvolveStep;
import com.memastick.backmem.evolution.entity.EvolveMeme;
import com.memastick.backmem.evolution.repository.EvolveMemeRepository;
import com.memastick.backmem.main.constant.GlobalConstant;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memes.repository.MemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneOffset;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class EvolveService {

    private final MemeRepository memeRepository;
    private final EvolveMemeRepository evolveMemeRepository;

    @Autowired
    public EvolveService(
        EvolveMemeRepository evolveMemeRepository,
        MemeRepository memeRepository
    ) {
        this.evolveMemeRepository = evolveMemeRepository;
        this.memeRepository = memeRepository;
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

    public long evolveDay() {
        return DAYS.between(
            GlobalConstant.START_EVOLVE,
            LocalDate.now(ZoneOffset.UTC)
        );
    }

}
