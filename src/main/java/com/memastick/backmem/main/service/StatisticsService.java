package com.memastick.backmem.main.service;

import com.memastick.backmem.main.api.StatisticsAPI;
import com.memastick.backmem.memes.repository.MemeLikeRepository;
import com.memastick.backmem.memes.repository.MemeRepository;
import com.memastick.backmem.person.repository.MemetickRepository;
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
            memetickRepository.sumDnaById(memetickId),
            memeRepository.countByMemetickId(memetickId),
            memeLikeRepository.sumChromosomeByMemetickId(memetickId)
        );
    }


    public StatisticsAPI global() {
        return new StatisticsAPI(
            memetickRepository.sumDna(),
            memeRepository.count(),
            memeLikeRepository.sumChromosome()
        );
    }

}
