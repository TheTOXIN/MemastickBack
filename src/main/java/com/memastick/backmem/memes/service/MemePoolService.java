package com.memastick.backmem.memes.service;

import com.memastick.backmem.evolution.constant.EvolveStep;
import com.memastick.backmem.evolution.entity.EvolveMeme;
import com.memastick.backmem.evolution.repository.EvolveMemeRepository;
import com.memastick.backmem.evolution.service.EvolveMemeService;
import com.memastick.backmem.main.util.JpaUtil;
import com.memastick.backmem.memes.constant.MemeType;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memes.repository.MemeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemePoolService {

    private final static Sort SORT_EVOLVE = Sort.by(
        Sort.Order.desc(("evolution")),
        Sort.Order.desc(("population")),
        Sort.Order.desc(("individuation"))
    );

    private final static Sort SORT_STEP = Sort.by(
        Sort.Order.desc(("meme.individuation"))
    );

    private final MemeRepository memeRepository;
    private final EvolveMemeRepository evolveMemeRepository;

    public List<Meme> generate(EvolveStep step, Pageable pageable) {
        if (step == null) {
            return memeRepository.findByTypeIn(
                Arrays.asList(MemeType.EVLV, MemeType.SLCT),
                JpaUtil.makePage(pageable, SORT_EVOLVE)
            );
        } else {
            return evolveMemeRepository.findByStep(
                step,
                JpaUtil.makePage(pageable, SORT_STEP)
            ).stream().map(EvolveMeme::getMeme).collect(Collectors.toList());
        }
    }
}
