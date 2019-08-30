package com.memastick.backmem.evolution.service;

import com.memastick.backmem.evolution.constant.EvolveStep;
import com.memastick.backmem.evolution.entity.EvolveMeme;
import com.memastick.backmem.evolution.handler.EvolveHandler;
import com.memastick.backmem.evolution.repository.EvolveMemeRepository;
import com.memastick.backmem.memes.service.MemesCreateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class EvolveNexterService {

    private static final Logger log = LoggerFactory.getLogger(EvolveNexterService.class);

    private final EvolveHandler evolveHandler;
    private final EvolveMemeRepository evolveMemeRepository;
    private final EvolveMemeService evolveMemeService;
    private final MemesCreateService memesCreateService;

    @Autowired
    public EvolveNexterService(
        EvolveHandler evolveHandler,
        EvolveMemeRepository evolveMemeRepository,
        EvolveMemeService evolveMemeService,
        MemesCreateService memesCreateService
    ) {
        this.evolveHandler = evolveHandler;
        this.evolveMemeRepository = evolveMemeRepository;
        this.evolveMemeService = evolveMemeService;
        this.memesCreateService = memesCreateService;
    }

    @Scheduled(cron = "0 0 */1 * * *", zone = "UTC")
    public void next() {
        log.info("START NEXT EVOLVE - {}", evolveMemeService.computePopulation());

        List<EvolveMeme> evolve = new ArrayList<>();

        Arrays.stream(EvolveStep.values()).forEach(step -> {
            List<EvolveMeme> evolveMemes = evolveMemeRepository.findByStep(step);

            evolveHandler.pullEvolve(step).evolution(evolveMemes);
            evolveMemeService.nextStep(evolveMemes);

            evolve.addAll(evolveMemes);
        });

        evolveMemeRepository.saveAll(evolve);

        log.info("END NEXT EVOLVE - {}", evolveMemeService.computePopulation());

        memesCreateService.notification();
    }
}
