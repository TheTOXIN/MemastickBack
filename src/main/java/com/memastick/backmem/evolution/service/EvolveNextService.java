package com.memastick.backmem.evolution.service;

import com.memastick.backmem.evolution.constant.EvolveStep;
import com.memastick.backmem.evolution.entity.EvolveMeme;
import com.memastick.backmem.evolution.handler.EvolveHandler;
import com.memastick.backmem.evolution.repository.EvolveMemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class EvolveNextService {

    private final EvolveHandler evolveHandler;
    private final EvolveMemeRepository evolveMemeRepository;
    private final EvolveMemeService evolveMemeService;

    @Autowired
    public EvolveNextService(
        EvolveHandler evolveHandler,
        EvolveMemeRepository evolveMemeRepository,
        EvolveMemeService evolveMemeService
    ) {
        this.evolveHandler = evolveHandler;
        this.evolveMemeRepository = evolveMemeRepository;
        this.evolveMemeService = evolveMemeService;
    }

    @Scheduled(cron = "0 0 0 * * *", zone = "UTC")
    public void next() {
        Arrays.stream(EvolveStep.values()).forEach(step -> {
            List<EvolveMeme> evolveMemes = evolveMemeRepository.findByStep(step);

            evolveMemes.forEach(evolveHandler.pullEvolve(step)::evolution);
            evolveMemes.forEach(evolveMemeService::nextStep);

            evolveMemeRepository.saveAll(evolveMemes);
        });
    }
}
