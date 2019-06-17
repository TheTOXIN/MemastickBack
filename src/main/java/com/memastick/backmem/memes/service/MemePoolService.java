package com.memastick.backmem.memes.service;

import com.memastick.backmem.evolution.constant.EvolveStep;
import com.memastick.backmem.evolution.service.EvolveMemeService;
import com.memastick.backmem.memes.constant.MemeType;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memes.repository.MemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemePoolService {

    private final MemeRepository memeRepository;
    private final EvolveMemeService evolveMemeService;

    @Autowired
    public MemePoolService(
        MemeRepository memeRepository,
        EvolveMemeService evolveMemeService
    ) {
        this.memeRepository = memeRepository;
        this.evolveMemeService = evolveMemeService;
    }

    public List<Meme> generate(EvolveStep step, Pageable pageable) {
        if (step == null) {
            return memeRepository.findByType(
                MemeType.EVOLVE,
                generatePageable(pageable)
            );
        } else {
            return memeRepository.findAllByStepEvolveDayAndType(
                evolveMemeService.evolveDay(),
                step.getNumber(),
                MemeType.EVOLVE,
                pageable
            );
        }
    }

    private Pageable generatePageable(Pageable pageable) {
        return PageRequest.of(
            pageable.getPageNumber(),
            pageable.getPageSize(),
            Sort.by(
                Sort.Order.desc(("population")),
                Sort.Order.desc(("indexer"))
            )
        );
    }
}
