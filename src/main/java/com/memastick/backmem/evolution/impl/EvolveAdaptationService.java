package com.memastick.backmem.evolution.impl;

import com.memastick.backmem.evolution.annotation.Evolve;
import com.memastick.backmem.evolution.constant.EvolveStep;
import com.memastick.backmem.evolution.entity.EvolveMeme;
import com.memastick.backmem.evolution.iface.Evolution;
import com.memastick.backmem.memes.entity.Meme;

import java.util.Comparator;
import java.util.List;

@Evolve(step = EvolveStep.ADAPTATION)
public class EvolveAdaptationService implements Evolution {

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
