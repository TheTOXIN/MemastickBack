package com.memastick.backmem.evolution.impl;

import com.memastick.backmem.evolution.annotation.Evolve;
import com.memastick.backmem.evolution.constant.EvolveStep;
import com.memastick.backmem.evolution.entity.EvolveMeme;
import com.memastick.backmem.evolution.iface.Evolution;

import java.util.List;

@Evolve(step = EvolveStep.MUTATION)
public class EvolveMutationService implements Evolution {

    @Override
    public void evolution(List<EvolveMeme> evolveMemes) {
        // LOGIC
    }
}
