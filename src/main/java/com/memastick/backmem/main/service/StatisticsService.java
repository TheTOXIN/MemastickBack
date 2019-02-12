package com.memastick.backmem.main.service;

import com.memastick.backmem.main.api.StatisticsAPI;
import com.memastick.backmem.memes.constant.MemeType;
import com.memastick.backmem.memes.repository.MemeLikeRepository;
import com.memastick.backmem.memes.repository.MemeRepository;
import com.memastick.backmem.memetick.repository.MemetickRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StatisticsService {

    private final MemeLikeRepository memeLikeRepository;
    private final MemetickRepository memetickRepository;
    private final MemeRepository memeRepository;

    @Autowired
    public StatisticsService(
        MemeLikeRepository memeLikeRepository,
        MemetickRepository memetickRepository,
        MemeRepository memeRepository
    ) {
        this.memeLikeRepository = memeLikeRepository;
        this.memetickRepository = memetickRepository;
        this.memeRepository = memeRepository;
    }

    public StatisticsAPI byMemetick(UUID memetickId) {
        return new StatisticsAPI(
            memetickRepository.sumDnaById(memetickId).orElse(0L),
            memeRepository.countByMemetickIdAndType(memetickId, MemeType.INDIVID).orElse(0L),
            memeLikeRepository.sumChromosomeByMemetickId(memetickId).orElse(0L)
        );
    }

    public StatisticsAPI global() {
        return new StatisticsAPI(
            memetickRepository.sumDna().orElse(0L),
            memeRepository.countByType(MemeType.INDIVID).orElse(0L),
            memeLikeRepository.sumChromosome().orElse(0L)
        );
    }
}
