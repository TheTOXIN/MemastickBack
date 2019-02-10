package com.memastick.backmem.evolution.service;

import com.memastick.backmem.evolution.constant.EvolveStep;
import com.memastick.backmem.evolution.entity.EvolveMeme;
import com.memastick.backmem.evolution.repository.EvolveMemeRepository;
import com.memastick.backmem.main.constant.GlobalConstant;
import com.memastick.backmem.memes.entity.Meme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneOffset;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class EvolveService {

    private final EvolveMemeRepository evolveMemeRepository;

    @Autowired
    public EvolveService(
        EvolveMemeRepository evolveMemeRepository
    ) {
        this.evolveMemeRepository = evolveMemeRepository;
    }

    public void startEvolve(Meme meme) {
        evolveMemeRepository.save(new EvolveMeme(
            meme,
            EvolveStep.BIRTH,
            evolveDay()
        ));
    }

    public long evolveDay() {
        return DAYS.between(
            GlobalConstant.START_EVOLVE,
            LocalDate.now(ZoneOffset.UTC)
        );
    }

}
