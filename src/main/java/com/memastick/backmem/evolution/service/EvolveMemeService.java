package com.memastick.backmem.evolution.service;

import com.memastick.backmem.evolution.api.EvolveMemeAPI;
import com.memastick.backmem.evolution.constant.EvolveStep;
import com.memastick.backmem.evolution.entity.EvolveMeme;
import com.memastick.backmem.evolution.repository.EvolveMemeRepository;
import com.memastick.backmem.main.constant.GlobalConstant;
import com.memastick.backmem.main.dto.EPI;
import com.memastick.backmem.main.util.TimeUtil;
import com.memastick.backmem.memes.constant.MemeType;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memes.repository.MemeRepository;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.security.component.OauthData;
import com.memastick.backmem.tokens.repository.TokenAcceptRepository;
import com.memastick.backmem.tokens.service.TokenAcceptService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@AllArgsConstructor
public class EvolveMemeService {

    private final EvolveMemeRepository evolveMemeRepository;
    private final TokenAcceptService tokenAcceptService;
    private final MemeRepository memeRepository;
    private final EvolveService evolveService;
    private final OauthData oauthData;

    public void startEvolve(Meme meme) {
        evolveMemeRepository.save(
            new EvolveMeme(
                meme
            )
        );
    }

    public void nextStep(List<EvolveMeme> evolveMemes) {
        evolveMemes.forEach(e -> {
            if (e.getStep() == null) return;

            EvolveStep nowStep = e.getStep();
            EvolveStep nextStep = EvolveStep.find(nowStep.getNumber() + 1);

            e.setStep(nextStep);
        });
    }

    public EvolveMemeAPI readByMeme(UUID memeId) {
        Memetick currentMemetick = oauthData.getCurrentMemetick();
        EvolveMeme evolveMeme = evolveMemeRepository.findByMemeId(memeId);

        Meme meme = evolveMeme.getMeme();
        Memetick memeMemetick = meme.getMemetick();

        boolean myMeme = currentMemetick.getId().equals(memeMemetick.getId());
        boolean canAccept = tokenAcceptService.canAccept(currentMemetick, memeMemetick, evolveMeme);

        EPI epi = evolveService.toEPI(meme);

        return new EvolveMemeAPI(
            meme.getId(),
            epi,
            evolveMeme.getStep(),
            myMeme,
            canAccept,
            evolveMeme.isImmunity(),
            evolveMeme.getAdaptation(),
            computeNextTimer()
        );
    }

    public Float readChance(UUID memeId) {
        EvolveMeme evolveMeme = evolveMemeRepository.findByMemeId(memeId);
        Meme meme = evolveMeme.getMeme();

        if (!meme.getType().equals(MemeType.SLCT)) return null;
        if (evolveMeme.isImmunity()) return 100F;

        return computeChance(meme);
    }

    public float computeChance(Meme meme) {
        long max = memeRepository.maxByCromosome(MemeType.SLCT).orElse(0L);
        long min = memeRepository.minByCromosome(MemeType.SLCT).orElse(0L);

        return computeChance(meme, max, min);
    }

    public float computeChance(Meme meme, long max, long min) {
        float onePercent = 100f / (max - min);
        float chance = (meme.getChromosomes() - min) * onePercent;

        if (max == min) chance = 100;
        if (chance > 100f) chance = 100f;

        return chance;
    }

    public LocalTime computeSelectTimer() {
        return TimeUtil.minusTime(
            LocalTime.MIDNIGHT,
            LocalTime.now()
        );
    }

    public LocalTime computeNextTimer() {
        LocalTime now = LocalTime.now();

        LocalTime next = now
            .plusHours(1)
            .withMinute(0)
            .withSecond(0);

        return TimeUtil.minusTime(next, now);
    }

    public EvolveStep computeStep(Meme meme) {
        long currentPop = evolveService.computePopulation();
        long memePop = meme.getPopulation();

        int step = (int) (currentPop - memePop) + 1;

        return EvolveStep.find(step);
    }
}
