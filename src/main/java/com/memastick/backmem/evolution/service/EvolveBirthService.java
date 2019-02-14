package com.memastick.backmem.evolution.service;

import com.memastick.backmem.evolution.annotation.Evolve;
import com.memastick.backmem.evolution.constant.EvolveStep;
import com.memastick.backmem.evolution.entity.EvolveMeme;
import com.memastick.backmem.evolution.interfaces.Evolution;
import com.memastick.backmem.main.util.MathUtil;
import com.memastick.backmem.memes.entity.Meme;

import java.util.Comparator;
import java.util.List;

@Evolve(step = EvolveStep.BIRTH)
public class EvolveBirthService implements Evolution {

    @Override
    public void evolution(List<EvolveMeme> evolveMemes) {
        evolveMemes.sort(Comparator.comparing(e -> e.getMeme().getChromosomes()));

        if (evolveMemes.isEmpty()) return;

        long min = evolveMemes.get(0).getMeme().getChromosomes();
        long max = evolveMemes.get(evolveMemes.size() - 1).getMeme().getChromosomes();

        evolveMemes.forEach(e -> {
            Meme meme = e.getMeme();

            float onePercent = 100f / (max - min);
            float chance = (meme.getChromosomes() - min) * onePercent;

            if (max == min) chance = 100;
            e.setChanceSurvive(chance);

            meme.setChromosomes(meme.getChromosomes() + MathUtil.rand(0, 100));
            meme.getMemetick().setDna(meme.getMemetick().getDna() + MathUtil.rand(0, 100));
        });
    }
}
