package com.memastick.backmem.evolution.impl;

import com.memastick.backmem.evolution.annotation.Evolve;
import com.memastick.backmem.evolution.constant.EvolveStep;
import com.memastick.backmem.evolution.entity.EvolveMeme;
import com.memastick.backmem.evolution.iface.Evolution;
import com.memastick.backmem.main.constant.GlobalConstant;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memes.entity.MemeComment;
import com.memastick.backmem.memes.repository.MemeCommentRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.memastick.backmem.main.constant.GlobalConstant.EVOLVE_CHROMOSOME;

@RequiredArgsConstructor
@Evolve(step = EvolveStep.MUTATION)
public class EvolveMutationService implements Evolution {

    private final MemeCommentRepository commentRepository;

    @Override
    public void evolution(List<EvolveMeme> evolveMemes) {
        evolveMemes.forEach(e -> {
            Meme meme = e.getMeme();

            long count = commentRepository.countByMemeId(meme.getId()).orElse(0L);
            int chromosome = (int) count * (EVOLVE_CHROMOSOME * 2);

            meme.setChromosomes(meme.getChromosomes() + chromosome);
        });
    }
}
