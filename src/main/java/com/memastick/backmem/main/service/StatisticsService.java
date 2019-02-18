package com.memastick.backmem.main.service;

import com.memastick.backmem.main.api.StatisticsAPI;
import com.memastick.backmem.memes.constant.MemeType;
import com.memastick.backmem.memes.repository.MemeRepository;
import com.memastick.backmem.memetick.repository.MemetickRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StatisticsService {

    private final MemetickRepository memetickRepository;
    private final MemeRepository memeRepository;

    @Autowired
    public StatisticsService(
        MemetickRepository memetickRepository,
        MemeRepository memeRepository
    ) {
        this.memetickRepository = memetickRepository;
        this.memeRepository = memeRepository;
    }

    public StatisticsAPI byMemetick(UUID memetickId) {
        return new StatisticsAPI(
            memetickRepository.findDnaByMemetickId(memetickId).orElse(0L),
            memeRepository.countByMemetickIdAndType(memetickId, MemeType.INDIVID).orElse(0L),
            memeRepository.sumChromosomeByMemetickId(memetickId).orElse(0L)
        );
    }

    public StatisticsAPI global() {
        return new StatisticsAPI(
            memetickRepository.sumDna().orElse(0L),
            memeRepository.countByType(MemeType.INDIVID).orElse(0L),
            memeRepository.sumChromosome().orElse(0L)
        );
    }
}
