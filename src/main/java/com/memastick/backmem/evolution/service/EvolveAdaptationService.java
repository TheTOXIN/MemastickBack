package com.memastick.backmem.evolution.service;

import com.memastick.backmem.evolution.annotation.Evolve;
import com.memastick.backmem.evolution.constant.EvolveStep;
import com.memastick.backmem.evolution.entity.EvolveMeme;
import com.memastick.backmem.evolution.interfaces.Evolution;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memes.repository.MemeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.List;

@Evolve(step = EvolveStep.ADAPTATION)
public class EvolveAdaptationService implements Evolution {

    private final MemeRepository memeRepository;

    @Autowired
    public EvolveAdaptationService(
        MemeRepository memeRepository
    ) {
        this.memeRepository = memeRepository;
    }

    @Override
    public void evolution(List<EvolveMeme> evolveMemes) {
        if (evolveMemes.isEmpty()) return;

        evolveMemes.sort(Comparator.comparing(e -> e.getMeme().getChromosomes()));

        long min = evolveMemes.get(0).getMeme().getChromosomes();
        long max = evolveMemes.get(evolveMemes.size() - 1).getMeme().getChromosomes();

        evolveMemes.forEach(e -> {
            Meme meme = e.getMeme();

            float onePercent = 100f / (max - min);
            float chance = (meme.getChromosomes() - min) * onePercent;

            if (max == min) chance = 100;
            if (chance > 100f) chance = 100f;

            e.setChance(chance);
        });
    }
}
