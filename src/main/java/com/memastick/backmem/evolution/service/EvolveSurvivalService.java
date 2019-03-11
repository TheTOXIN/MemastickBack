package com.memastick.backmem.evolution.service;

import com.memastick.backmem.evolution.annotation.Evolve;
import com.memastick.backmem.evolution.constant.EvolveStep;
import com.memastick.backmem.evolution.entity.EvolveMeme;
import com.memastick.backmem.evolution.interfaces.Evolution;
import com.memastick.backmem.main.util.MathUtil;
import com.memastick.backmem.memes.constant.MemeType;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memes.repository.MemeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.List;


@Evolve(step = EvolveStep.SURVIVAL)
public class EvolveSurvivalService implements Evolution {

    private final MemeRepository memeRepository;

    @Autowired
    public EvolveSurvivalService(
        MemeRepository memeRepository
    ) {
        this.memeRepository = memeRepository;
    }

    @Override
    public void evolution(List<EvolveMeme> evolveMemes) {
        if (evolveMemes.isEmpty()) return;

        evolveMemes.sort(Comparator.comparing(e -> e.getMeme().getChromosomes()));

        float avg = evolveMemes.get(evolveMemes.size() / 2).getChanceSurvive();

        evolveMemes.forEach(e -> {
            Meme meme = e.getMeme();

            int dnaBonus = MathUtil.rand(0, 100);
            boolean isSurvive = e.getChanceSurvive() >= avg;

            if (isSurvive) {
                meme.setType(MemeType.INDIVID);
                dnaBonus *= 1;
            } else {
                meme.setType(MemeType.DEATH);
                dnaBonus *= -1;
            }

            e.setChanceSurvive(null);
            meme.getMemetick().setDna(meme.getMemetick().getDna() + dnaBonus);

            memeRepository.save(meme);
        });
    }
}
