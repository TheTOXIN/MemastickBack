package com.memastick.backmem.tokens.service;

import com.memastick.backmem.errors.exception.TokenAcceptException;
import com.memastick.backmem.evolution.entity.EvolveMeme;
import com.memastick.backmem.evolution.repository.EvolveMemeRepository;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memes.repository.MemeRepository;
import com.memastick.backmem.memes.service.MemeService;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.security.service.SecurityService;
import com.memastick.backmem.tokens.constant.TokenType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TokenAcceptService {

    private final EvolveMemeRepository evolveMemeRepository;
    private final MemeService memeService;
    private final TokenWalletService tokenWalletService;
    private final SecurityService securityService;
    private final MemeRepository memeRepository;

    @Autowired
    public TokenAcceptService(
        EvolveMemeRepository evolveMemeRepository,
        MemeService memeService,
        TokenWalletService tokenWalletService,
        SecurityService securityService,
        MemeRepository memeRepository
    ) {
        this.evolveMemeRepository = evolveMemeRepository;
        this.memeService = memeService;
        this.tokenWalletService = tokenWalletService;
        this.securityService = securityService;
        this.memeRepository = memeRepository;
    }

    public void accept(TokenType token, UUID memeId) {
        Meme meme = memeService.findById(memeId);
        Memetick memetick = securityService.getCurrentMemetick();

        tokenWalletService.have(token, memetick);
        EvolveMeme evolve = evolveMemeRepository.findByMeme(meme);

        if (!evolve.getStep().equals(token.getStep())) throw new TokenAcceptException();

        switch (token) {
            case TUBE: adaptation(meme); break;
            case SCOPE: break;
            case MUTAGEN: break;
            case CROSSOVER: break;
            case ANTIBIOTIC: selection(evolve); break;
        }

        tokenWalletService.take(token, memetick);
    }


    private void adaptation(Meme meme) {
        meme.setAdaptation(meme.getAdaptation() + 1);
        memeRepository.save(meme);
    }

    private void selection(EvolveMeme evolve) {
        if (evolve.isImmunity()) throw new TokenAcceptException();
        evolve.setImmunity(true);
        evolveMemeRepository.save(evolve);
    }
}
