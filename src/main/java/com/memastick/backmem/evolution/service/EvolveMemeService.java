package com.memastick.backmem.evolution.service;

import com.memastick.backmem.evolution.api.EvolveMemeAPI;
import com.memastick.backmem.evolution.constant.EvolveStep;
import com.memastick.backmem.evolution.entity.EvolveMeme;
import com.memastick.backmem.evolution.repository.EvolveMemeRepository;
import com.memastick.backmem.main.constant.GlobalConstant;
import com.memastick.backmem.main.dto.EPI;
import com.memastick.backmem.memecoin.service.MemeCoinService;
import com.memastick.backmem.memes.constant.MemeType;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memes.repository.MemeRepository;
import com.memastick.backmem.memes.service.MemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class EvolveMemeService {

    private final EvolveMemeRepository evolveMemeRepository;
    private final MemeService memeService;
    private final MemeRepository memeRepository;
    private final MemeCoinService coinService;

    @Autowired
    public EvolveMemeService(
        EvolveMemeRepository evolveMemeRepository,
        MemeService memeService,
        MemeRepository memeRepository,
        MemeCoinService coinService
    ) {
        this.evolveMemeRepository = evolveMemeRepository;
        this.memeService = memeService;
        this.memeRepository = memeRepository;
        this.coinService = coinService;
    }

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

    public EPI computeEPI() {
        return new EPI(
            this.computeEvolution(),
            this.computePopulation(),
            this.computeIndividuation()
        );
    }

    public long computeEvolution() {
        return DAYS.between(GlobalConstant.START_EVOLVE, LocalDate.now(ZoneOffset.UTC));
    }

    public long computePopulation() {
        return LocalTime.now(ZoneOffset.UTC).getHour() + 1;
    }

    public long computeIndividuation() {
        return memeRepository.countByEvolutionAndPopulation(computeEvolution(), computePopulation()).orElse(0L);
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
        LocalTime now = LocalTime.now();

        return LocalTime.MIDNIGHT
            .minusHours(now.getHour())
            .minusMinutes(now.getMinute())
            .minusSeconds(now.getSecond());
    }

    public LocalTime computeNextTimer() {
        LocalTime now = LocalTime.now();

        LocalTime next = now
            .plusHours(1)
            .withMinute(0)
            .withSecond(0);

        return next
            .minusHours(now.getHour())
            .minusMinutes(now.getMinute())
            .minusSeconds(now.getSecond());
    }

    public EvolveMemeAPI readByMeme(UUID memeId) {
        Meme meme = memeService.findById(memeId);
        EvolveMeme evolveMeme = evolveMemeRepository.findByMeme(meme);

        return new EvolveMemeAPI(
            meme.getId(),
            new EPI(
              meme.getEvolution(),
              meme.getPopulation(),
              meme.getIndividuation()
            ),
            evolveMeme.getStep(),
            evolveMeme.isImmunity(),
            evolveMeme.getAdaptation(),
            computeNextTimer()
        );
    }

    public Float readChance(UUID memeId) {
        Meme meme = memeService.findById(memeId);
        if (!meme.getType().equals(MemeType.SLCT)) return null;

        EvolveMeme evolveMeme = evolveMemeRepository.findByMeme(meme);
        if (evolveMeme.isImmunity()) return 100F;

        return computeChance(meme);
    }

    @Transactional
    public void resurrectMeme(UUID memeId) {
        EvolveMeme evolveMeme = evolveMemeRepository.findByMemeId(memeId);
        Meme meme = evolveMeme.getMeme();

        if (!MemeType.DEAD.equals(meme.getType())) return;
        coinService.transaction(meme.getMemetick(), -150);

        meme.setType(MemeType.SLCT);
        memeRepository.save(meme);
    }
}
