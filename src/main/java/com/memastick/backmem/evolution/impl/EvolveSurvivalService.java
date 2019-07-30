package com.memastick.backmem.evolution.impl;

import com.memastick.backmem.evolution.annotation.Evolve;
import com.memastick.backmem.evolution.constant.EvolveStep;
import com.memastick.backmem.evolution.entity.EvolveMeme;
import com.memastick.backmem.evolution.iface.Evolution;
import com.memastick.backmem.memes.constant.MemeType;

import java.util.List;


@Evolve(step = EvolveStep.SURVIVAL)
public class EvolveSurvivalService implements Evolution {

    @Override
    public void evolution(List<EvolveMeme> evolveMemes) {
        evolveMemes.forEach(e -> e.getMeme().setType(MemeType.SLCT));
    }
}
