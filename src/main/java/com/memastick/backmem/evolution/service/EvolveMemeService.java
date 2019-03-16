package com.memastick.backmem.evolution.service;

import com.memastick.backmem.evolution.api.EvolveMemeAPI;
import com.memastick.backmem.evolution.constant.EvolveStep;
import com.memastick.backmem.evolution.entity.EvolveMeme;
import com.memastick.backmem.evolution.repository.EvolveMemeRepository;
import com.memastick.backmem.main.constant.GlobalConstant;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memes.service.MemeService;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.security.service.SecurityService;
import com.memastick.backmem.tokens.constant.TokenType;
import com.memastick.backmem.tokens.service.TokenWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class EvolveMemeService {

    private final EvolveMemeRepository evolveMemeRepository;
    private final MemeService memeService;
    private final TokenWalletService tokenWalletService;
    private final SecurityService securityService;

    @Autowired
    public EvolveMemeService(
        EvolveMemeRepository evolveMemeRepository,
        MemeService memeService,
        TokenWalletService tokenWalletService,
        SecurityService securityService
    ) {
        this.evolveMemeRepository = evolveMemeRepository;
        this.memeService = memeService;
        this.tokenWalletService = tokenWalletService;
        this.securityService = securityService;
    }

    public void startEvolve(Meme meme) {
        evolveMemeRepository.save(new EvolveMeme(
            meme,
            EvolveStep.BIRTH,
            evolveDay()
        ));
    }

    public void nextStep(List<EvolveMeme> evolveMemes) {
        evolveMemes.forEach(e -> {
            if (e.getStep() == null) return;

            EvolveStep nowStep = e.getStep();
            EvolveStep nextStep = EvolveStep.find(nowStep.getStep() + 1);

            e.setStep(nextStep);
        });
    }

    public long evolveDay() {
        return DAYS.between(
            GlobalConstant.START_EVOLVE,
            LocalDate.now(ZoneOffset.UTC)
        );
    }

    public EvolveMemeAPI readByMeme(UUID memeId) {
        Meme meme = memeService.findById(memeId);
        EvolveMeme evolveMeme = evolveMemeRepository.findByMeme(meme);

        return new EvolveMemeAPI(
            meme.getId(),
            evolveMeme.getStep(),
            evolveMeme.getPopulation(),
            evolveMeme.getChanceSurvive().intValue(),
            evolveMeme.isChanceIncrease()
        );
    }

    public void chance(UUID memeId) {
        Meme meme = memeService.findById(memeId);
        Memetick memetick = securityService.getCurrentMemetick();

        tokenWalletService.have(TokenType.SELECTION, memetick);
        EvolveMeme evolveMeme = evolveMemeRepository.findByMeme(meme);

        if (!EvolveStep.SURVIVAL.equals(evolveMeme.getStep())) return;
        if (evolveMeme.isChanceIncrease())return;

        evolveMeme.setChanceIncrease(true);
        evolveMemeRepository.save(evolveMeme);

        tokenWalletService.take(TokenType.SELECTION, memetick);
    }
}
