package com.memastick.backmem.evolution.impl;

import com.memastick.backmem.evolution.annotation.Evolve;
import com.memastick.backmem.evolution.constant.EvolveStep;
import com.memastick.backmem.evolution.entity.EvolveMeme;
import com.memastick.backmem.evolution.iface.Evolution;
import com.memastick.backmem.main.projection.MemeLohSum;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memes.repository.MemeLohRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Evolve(step = EvolveStep.FITNESS)
public class EvolveFitnessService implements Evolution {

    private final MemeLohRepository memeLohRepository;

    @Override
    public void evolution(List<EvolveMeme> evolveMemes) {
        evolveMemes.forEach(e -> {
            Meme meme = e.getMeme();

            MemeLohSum lohSum = memeLohRepository.sumByMemeId(meme.getId());
            int lohAvg = lohSum.computeAvg();

            meme.setChromosomes(meme.getChromosomes() + lohAvg);
        });
    }
}
