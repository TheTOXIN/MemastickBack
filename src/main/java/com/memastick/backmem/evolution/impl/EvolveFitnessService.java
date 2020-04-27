package com.memastick.backmem.evolution.impl;

import com.memastick.backmem.evolution.annotation.Evolve;
import com.memastick.backmem.evolution.constant.EvolveStep;
import com.memastick.backmem.evolution.entity.EvolveMeme;
import com.memastick.backmem.evolution.iface.Evolution;

import java.util.List;

@Evolve(step = EvolveStep.FITNESS)
public class EvolveFitnessService implements Evolution {

    @Override
    public void evolution(List<EvolveMeme> evolveMemes) {
        // TODO LOGIC
    }
}
