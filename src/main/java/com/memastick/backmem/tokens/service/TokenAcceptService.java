package com.memastick.backmem.tokens.service;

import com.memastick.backmem.errors.exception.TokenAcceptException;
import com.memastick.backmem.evolution.entity.EvolveMeme;
import com.memastick.backmem.evolution.repository.EvolveMemeRepository;
import com.memastick.backmem.main.constant.DnaCount;
import com.memastick.backmem.main.util.MathUtil;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memes.repository.MemeRepository;
import com.memastick.backmem.memes.service.MemeService;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.service.MemetickService;
import com.memastick.backmem.notification.service.NotifyService;
import com.memastick.backmem.security.component.OauthData;
import com.memastick.backmem.tokens.constant.TokenType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.memastick.backmem.errors.consts.ErrorCode.NOT_ACCEPTABLE;
import static com.memastick.backmem.errors.consts.ErrorCode.TOKEN_SELF;

@Service
@AllArgsConstructor
public class TokenAcceptService {

    private final EvolveMemeRepository evolveMemeRepository;
    private final MemeRepository memeRepository;
    private final TokenWalletService tokenWalletService;
    private final OauthData oauthData;
    private final MemetickService memetickService;
    private final NotifyService notifyService;
    private final MemeService memeService;

    public void accept(TokenType token, UUID memeId) {
        Meme meme = memeRepository.tryFindById(memeId);
        Memetick memetick = oauthData.getCurrentMemetick();

        if (meme.getMemetick().getId().equals(memetick.getId())) throw new TokenAcceptException(TOKEN_SELF);

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

        int dna = DnaCount.TOKEN * (token.getStep().getNumber() + 1);

        tokenWalletService.take(token, memetick);
        memetickService.addDna(memetick, dna);
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
