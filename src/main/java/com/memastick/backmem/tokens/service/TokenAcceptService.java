package com.memastick.backmem.tokens.service;

import com.memastick.backmem.errors.exception.TokenAcceptException;
import com.memastick.backmem.evolution.entity.EvolveMeme;
import com.memastick.backmem.evolution.repository.EvolveMemeRepository;
import com.memastick.backmem.main.util.MathUtil;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memes.service.MemeService;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.service.MemetickService;
import com.memastick.backmem.notification.constant.NotifyType;
import com.memastick.backmem.notification.dto.NotifyDTO;
import com.memastick.backmem.notification.service.NotifyService;
import com.memastick.backmem.security.service.SecurityService;
import com.memastick.backmem.tokens.constant.TokenType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.memastick.backmem.errors.consts.ErrorCode.NOT_ACCEPTABLE;
import static com.memastick.backmem.errors.consts.ErrorCode.TOKEN_SELF;

@Service
public class TokenAcceptService {

    private final EvolveMemeRepository evolveMemeRepository;
    private final MemeService memeService;
    private final TokenWalletService tokenWalletService;
    private final SecurityService securityService;
    private final MemetickService memetickService;
    private final NotifyService notifyService;

    @Autowired
    public TokenAcceptService(
        EvolveMemeRepository evolveMemeRepository,
        MemeService memeService,
        TokenWalletService tokenWalletService,
        SecurityService securityService,
        MemetickService memetickService,
        NotifyService notifyService
    ) {
        this.evolveMemeRepository = evolveMemeRepository;
        this.memeService = memeService;
        this.tokenWalletService = tokenWalletService;
        this.securityService = securityService;
        this.memetickService = memetickService;
        this.notifyService = notifyService;
    }

    public void accept(TokenType token, UUID memeId) {
        Meme meme = memeService.findById(memeId);
        Memetick memetick = securityService.getCurrentMemetick();

        if (meme.getMemetick().equals(memetick)) throw new TokenAcceptException(TOKEN_SELF);

        tokenWalletService.have(token, memetick);
        EvolveMeme evolve = evolveMemeRepository.findByMeme(meme);

        if (!evolve.getStep().equals(token.getStep())) throw new TokenAcceptException(NOT_ACCEPTABLE);

        switch (token) {
            case TUBE: adaptation(evolve); break;
            case SCOPE: break;
            case MUTAGEN: break;
            case CROSSOVER: break;
            case ANTIBIOTIC: selection(evolve); break;
        }

        tokenWalletService.take(token, memetick);
        memetickService.addDna(memetick, MathUtil.rand(0, 100));
        notifyService.sendTOKEN(token, meme);
    }

    private void adaptation(EvolveMeme evolve) {
        evolve.setAdaptation(evolve.getAdaptation() + 1);
        memeService.moveIndex(evolve.getMeme());
        evolveMemeRepository.save(evolve);
    }

    private void selection(EvolveMeme evolve) {
        if (evolve.isImmunity()) throw new TokenAcceptException(NOT_ACCEPTABLE);
        evolve.setImmunity(true);
        evolveMemeRepository.save(evolve);
    }
}
