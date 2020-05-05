package com.memastick.backmem.evolution.impl;

import com.memastick.backmem.evolution.annotation.Evolve;
import com.memastick.backmem.evolution.constant.EvolveStep;
import com.memastick.backmem.evolution.entity.EvolveMeme;
import com.memastick.backmem.evolution.iface.Evolution;
import com.memastick.backmem.memes.entity.Meme;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.memastick.backmem.main.constant.GlobalConstant.EVOLVE_CHROMOSOME;

@RequiredArgsConstructor
@Evolve(step = EvolveStep.ADAPTATION)
public class EvolveAdaptationService implements Evolution {

    @Override
    public void evolution(List<EvolveMeme> evolveMemes) {
        evolveMemes.forEach(e -> {
            Meme meme = e.getMeme();

            int adaptationChromosome = e.getAdaptation() * EVOLVE_CHROMOSOME;
            // TODO SET NEXT INDEX HERE

            meme.setChromosomes(meme.getChromosomes() + adaptationChromosome);
        });
    }
}
