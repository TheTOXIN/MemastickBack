package com.memastick.backmem.main.service;

import com.memastick.backmem.evolution.service.EvolveMemeService;
import com.memastick.backmem.main.api.StatisticsAPI;
import com.memastick.backmem.memes.constant.MemeType;
import com.memastick.backmem.memes.repository.MemeRepository;
import com.memastick.backmem.memetick.repository.MemetickRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class StatisticsService {

    private Map<Long, StatisticsAPI> globalCache = new HashMap<>();

    private final MemetickRepository memetickRepository;
    private final MemeRepository memeRepository;
    private final EvolveMemeService evolveMemeService;

    @Autowired
    public StatisticsService(
        MemetickRepository memetickRepository,
        MemeRepository memeRepository,
        EvolveMemeService evolveMemeService
    ) {
        this.memetickRepository = memetickRepository;
        this.memeRepository = memeRepository;
        this.evolveMemeService = evolveMemeService;
    }

    public StatisticsAPI byMemetick(UUID memetickId) {
        return new StatisticsAPI(
            memetickRepository.findDnaByMemetickId(memetickId).orElse(0L),
            memeRepository.countByMemetickIdAndType(memetickId, MemeType.INDV).orElse(0L),
            memeRepository.sumChromosomeByMemetickId(memetickId).orElse(0L)
        );
    }

    public StatisticsAPI global() {
        long evolveDay = evolveMemeService.evolveDay();

        StatisticsAPI stats = globalCache.getOrDefault(evolveDay, new StatisticsAPI(
            memetickRepository.sumDna().orElse(0L),
            memeRepository.countByType(MemeType.INDV).orElse(0L),
            memeRepository.sumChromosome().orElse(0L)
        ));

        globalCache.put(evolveDay, stats);

        return stats;
    }
}
