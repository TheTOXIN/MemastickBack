package com.memastick.backmem.evolution.impl;

import com.memastick.backmem.evolution.annotation.Evolve;
import com.memastick.backmem.evolution.constant.EvolveStep;
import com.memastick.backmem.evolution.entity.EvolveMeme;
import com.memastick.backmem.evolution.iface.Evolution;
import com.memastick.backmem.main.constant.GlobalConstant;
import com.memastick.backmem.memes.constant.MemeType;
import com.memastick.backmem.memes.entity.Meme;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.memastick.backmem.main.constant.GlobalConstant.EVOLVE_CHROMOSOME;

@RequiredArgsConstructor
@Evolve(step = EvolveStep.SURVIVAL)
public class EvolveSurvivalService implements Evolution {

    @Override
    public void evolution(List<EvolveMeme> evolveMemes) {
        evolveMemes.forEach(e -> {
            Meme meme = e.getMeme();

//            if (e.isImmunity()) {
//                meme.setChromosomes(meme.getChromosomes() / 2);
//            } TODO ПРИДУМАТЬ ДРУГОЕ НАКАЗАНИЕ

            meme.setType(MemeType.SLCT);
        });
    }
}
