package com.memastick.backmem.evolution.service;

import com.memastick.backmem.evolution.annotation.Evolve;
import com.memastick.backmem.evolution.constant.EvolveStep;
import com.memastick.backmem.evolution.entity.EvolveMeme;
import com.memastick.backmem.evolution.interfaces.Evolution;
import com.memastick.backmem.main.util.MathUtil;
import com.memastick.backmem.memes.constant.MemeType;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.person.service.MemetickService;
import org.springframework.beans.factory.annotation.Autowired;


@Evolve(step = EvolveStep.SURVIVAL)
public class EvolveSurvivalService implements Evolution {

    private final MemetickService memetickService;

    @Autowired
    public EvolveSurvivalService(
        MemetickService memetickService
    ) {
        this.memetickService = memetickService;
    }

    @Override
    public void evolution(EvolveMeme evolveMeme) {
        Meme meme = evolveMeme.getMeme();

        int dnaBonus = MathUtil.rand(0, 100);
        boolean isSurvive = evolveMeme.getChanceSurvive() >= 50.0;

        if (isSurvive) {
            meme.setType(MemeType.INDIVID);
            dnaBonus *= 1;
        } else {
            meme.setType(MemeType.DEATH);
            dnaBonus *= -1;
        }

        memetickService.addDna(meme.getMemetick(), dnaBonus);
        evolveMeme.setStep(null);
    }

}
