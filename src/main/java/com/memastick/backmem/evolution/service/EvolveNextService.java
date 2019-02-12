package com.memastick.backmem.evolution.service;

import com.memastick.backmem.evolution.handler.EvolveHandler;
import com.memastick.backmem.evolution.repository.EvolveMemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class EvolveNextService {

    private final EvolveHandler evolveHandler;
    private final EvolveMemeRepository evolveMemeRepository;

    @Autowired
    public EvolveNextService(
        EvolveHandler evolveHandler,
        EvolveMemeRepository evolveMemeRepository
    ) {
        this.evolveHandler = evolveHandler;
        this.evolveMemeRepository = evolveMemeRepository;
    }

    @Scheduled(cron = "0 0 0 * * *", zone = "UTC")
    public void trigger() {
        evolveMemeRepository
            .findAll()
            .forEach(e ->
                evolveHandler.pullEvolve(e.getStep()).evolution(e)
            );
    }

}
