package com.memastick.backmem.evolution.impl;

import com.memastick.backmem.evolution.annotation.Evolve;
import com.memastick.backmem.evolution.constant.EvolveStep;
import com.memastick.backmem.evolution.entity.EvolveMeme;
import com.memastick.backmem.evolution.iface.Evolution;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Evolve(step = EvolveStep.CROSSING)
public class EvolveCrossingService implements Evolution {

    @Override
    public void evolution(List<EvolveMeme> evolveMemes) {
        //LOGIC
    }
}
