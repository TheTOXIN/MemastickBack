package com.memastick.backmem.evolution.service;

import com.memastick.backmem.evolution.annotation.Evolve;
import com.memastick.backmem.evolution.constant.EvolveStep;
import com.memastick.backmem.evolution.entity.EvolveMeme;
import com.memastick.backmem.evolution.interfaces.Evolution;
import com.memastick.backmem.main.util.MathUtil;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memes.service.MemeLikeService;
import com.memastick.backmem.memetick.service.MemetickService;
import org.springframework.beans.factory.annotation.Autowired;

@Evolve(step = EvolveStep.BIRTH)
public class EvolveBirthService implements Evolution {

    private final MemeLikeService memeLikeService;
    private final MemetickService memetickService;
    private final EvolveMemeService evolveMemeService;

    @Autowired
    public EvolveBirthService(
        MemeLikeService memeLikeService,
        MemetickService memetickService,
        EvolveMemeService evolveMemeService
    ) {
        this.memeLikeService = memeLikeService;
        this.memetickService = memetickService;
        this.evolveMemeService = evolveMemeService;
    }

    @Override
    public void evolution(EvolveMeme evolveMeme) {
        Meme meme = evolveMeme.getMeme();

        float chance = evolveMemeService.computeChance(meme.getChromosomes());

        memetickService.addDna(meme.getMemetick(), MathUtil.rand(0, 100));

        //TODO КТО ЭТО ПЕРЕДЕРГИВАЕТ?
        memeLikeService.chromosomeTrigger(meme, MathUtil.rand(0, 100));

        evolveMeme.setChanceSurvive(chance);
    }
}
