package com.memastick.backmem.evolution.service;

import com.memastick.backmem.evolution.annotation.Evolve;
import com.memastick.backmem.evolution.constant.EvolveStep;
import com.memastick.backmem.evolution.entity.EvolveMeme;
import com.memastick.backmem.evolution.interfaces.Evolution;
import com.memastick.backmem.main.util.MathUtil;
import com.memastick.backmem.memes.constant.MemeType;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memetick.service.MemetickService;
import com.memastick.backmem.notification.constant.NotifyType;
import com.memastick.backmem.notification.dto.NotifyDTO;
import com.memastick.backmem.notification.service.NotifyService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.List;


@Evolve(step = EvolveStep.SURVIVAL)
public class EvolveSurvivalService implements Evolution {

    private final NotifyService notifyService;
    private final MemetickService memetickService;

    @Autowired
    public EvolveSurvivalService(
        NotifyService notifyService,
        MemetickService memetickService
    ) {
        this.notifyService = notifyService;
        this.memetickService = memetickService;
    }

    @Override
    public void evolution(List<EvolveMeme> evolveMemes) {
        if (evolveMemes.isEmpty()) return;

        evolveMemes.sort(Comparator.comparing(EvolveMeme::getChance));
        float avg = evolveMemes.get(evolveMemes.size() / 2).getChance();

        evolveMemes.forEach(e -> {
            Meme meme = e.getMeme();

            int dnaBonus = MathUtil.rand(0, 100);
            boolean isSurvive = e.getChance() >= avg || e.isImmunity();

            MemeType type;

            if (isSurvive) {
                type = MemeType.INDIVID;
                dnaBonus *= 1;
            } else {
                type = MemeType.DEATH;
                dnaBonus *= -1;
            }

            meme.setType(type);

            memetickService.addDna(meme.getMemetick(), dnaBonus);
            notifyService.sendMEME(meme);
        });
    }
}
