package com.memastick.backmem.tokens.service;

import com.memastick.backmem.errors.exception.TokenAcceptException;
import com.memastick.backmem.evolution.entity.EvolveMeme;
import com.memastick.backmem.evolution.repository.EvolveMemeRepository;
import com.memastick.backmem.main.constant.DnaCount;
import com.memastick.backmem.main.util.MathUtil;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memes.repository.MemeRepository;
import com.memastick.backmem.memes.service.MemeCommentService;
import com.memastick.backmem.memes.service.MemeLohService;
import com.memastick.backmem.memes.service.MemeService;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.service.MemetickService;
import com.memastick.backmem.notification.service.NotifyService;
import com.memastick.backmem.security.component.OauthData;
import com.memastick.backmem.tokens.api.TokenAcceptAPI;
import com.memastick.backmem.tokens.constant.TokenType;
import com.memastick.backmem.tokens.entity.TokenAccept;
import com.memastick.backmem.tokens.entity.TokenWallet;
import com.memastick.backmem.tokens.repository.TokenAcceptRepository;
import com.memastick.backmem.tokens.repository.TokenWalletRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.UUID;

import static com.memastick.backmem.errors.consts.ErrorCode.*;

@Service
@AllArgsConstructor
public class TokenAcceptService {

    private final EvolveMemeRepository evolveMemeRepository;
    private final TokenWalletService tokenWalletService;
    private final OauthData oauthData;
    private final MemetickService memetickService;
    private final NotifyService notifyService;
    private final MemeService memeService;
    private final MemeLohService memeLohService;
    private final MemeCommentService memeCommentService;
    private final TokenAcceptRepository tokenAcceptRepository;
    private final TokenWalletRepository tokenWalletRepository;

    @Transactional
    public void accept(TokenType token, UUID memeId, TokenAcceptAPI request) {
        Memetick memetick = oauthData.getCurrentMemetick();

        EvolveMeme evolve = evolveMemeRepository.findByMemeId(memeId);
        Meme meme = evolve.getMeme();

        if (meme.getMemetick().getId().equals(memetick.getId())) throw new TokenAcceptException(TOKEN_SELF);
        if (!evolve.getStep().equals(token.getStep())) throw new TokenAcceptException(NOT_ACCEPTABLE);

        boolean tokenAcceptExist = tokenAcceptRepository.checkExist(memetick, meme, token);
        if (tokenAcceptExist) throw new TokenAcceptException(TOKEN_EXIST);

        TokenWallet tokenWallet = tokenWalletRepository.findByMemetickId(memetick.getId());
        tokenWalletService.have(token, tokenWallet);

        switch (token) {
            case TUBE: adaptation(evolve); break;
            case SCOPE: fitness(meme, request); break;
            case MUTAGEN: mutation(meme, request); break;
            case CROSSOVER: break;
            case ANTIBIOTIC: selection(evolve); break;
        }

        int dna = DnaCount.TOKEN * (token.getStep().getNumber() + 1);

        tokenAcceptRepository.create(meme, memetick, token);
        tokenWalletService.take(token, tokenWallet);
        memetickService.addDna(memetick, dna);
        notifyService.sendTOKEN(token, meme);
    }

    private void adaptation(EvolveMeme evolve) {
        evolve.setAdaptation(evolve.getAdaptation() + 1);
        memeService.moveIndex(evolve.getMeme());
        evolveMemeRepository.save(evolve);
    }

    private void fitness(Meme meme, TokenAcceptAPI request) {
        memeLohService.saveByMeme(
            meme,
            request.getLoh()
        );
    }

    private void mutation(Meme meme, TokenAcceptAPI request) {
        memeCommentService.createComment(
            meme,
            request.getComment()
        );
    }

    private void selection(EvolveMeme evolve) {
        if (evolve.isImmunity()) throw new TokenAcceptException(NOT_ACCEPTABLE);
        evolve.setImmunity(true);
        evolveMemeRepository.save(evolve);
    }

    public boolean canAccept(Memetick memetick, EvolveMeme evolveMeme) {
        Meme meme = evolveMeme.getMeme();
        TokenType token = TokenType.find(evolveMeme.getStep());

        boolean myMeme = memetick.getId().equals(meme.getMemetick().getId());
        boolean exist = tokenAcceptRepository.checkExist(memetick, meme, token);

        return !myMeme && !exist;
    }
}
