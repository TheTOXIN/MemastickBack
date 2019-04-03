package com.memastick.backmem.evolution.service;

import com.memastick.backmem.evolution.annotation.Evolve;
import com.memastick.backmem.evolution.constant.EvolveStep;
import com.memastick.backmem.evolution.entity.EvolveMeme;
import com.memastick.backmem.evolution.interfaces.Evolution;
import com.memastick.backmem.main.util.MathUtil;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memes.repository.MemeLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.List;

@Evolve(step = EvolveStep.BIRTH)
public class EvolveBirthService implements Evolution {

    private final MemeLikeRepository memeLikeRepository;

    @Autowired
    public EvolveBirthService(
        MemeLikeRepository memeLikeRepository
    ) {
        this.memeLikeRepository = memeLikeRepository;
    }

    @Override
    public void evolution(List<EvolveMeme> evolveMemes) {
        if (evolveMemes.isEmpty()) return;

        evolveMemes.forEach(e -> {
            Meme meme = e.getMeme();

            Long likes = memeLikeRepository.countByMemeIdAndIsLikeTrue(meme.getId()).orElse(0L);

            meme.setChromosomes(meme.getChromosomes() + (int) (likes * 10));//TODO remove for adaptation
            meme.getMemetick().setDna(meme.getMemetick().getDna() + MathUtil.rand(0, meme.getChromosomes()));
        });

        evolveMemes.sort(Comparator.comparing(e -> e.getMeme().getChromosomes()));

        long min = evolveMemes.get(0).getMeme().getChromosomes();
        long max = evolveMemes.get(evolveMemes.size() - 1).getMeme().getChromosomes();

        evolveMemes.forEach(e -> {
            Meme meme = e.getMeme();

            float onePercent = 100f / (max - min);
            float chance = (meme.getChromosomes() - min) * onePercent;

            if (max == min) chance = 100;
            e.setChance(chance);
        });
    }
}
