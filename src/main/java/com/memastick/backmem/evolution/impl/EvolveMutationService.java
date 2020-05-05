package com.memastick.backmem.evolution.impl;

import com.memastick.backmem.evolution.annotation.Evolve;
import com.memastick.backmem.evolution.constant.EvolveStep;
import com.memastick.backmem.evolution.entity.EvolveMeme;
import com.memastick.backmem.evolution.iface.Evolution;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memes.entity.MemeComment;
import com.memastick.backmem.memes.repository.MemeCommentRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Evolve(step = EvolveStep.MUTATION)
public class EvolveMutationService implements Evolution {

    private final MemeCommentRepository commentRepository;

    @Override
    public void evolution(List<EvolveMeme> evolveMemes) {
        evolveMemes.forEach(e -> {
            Meme meme = e.getMeme();

            MemeComment bestComment = commentRepository.findBestComment(meme.getId());
            if (bestComment == null) return;

            meme.setComment(bestComment.getComment());
        });
    }
}
