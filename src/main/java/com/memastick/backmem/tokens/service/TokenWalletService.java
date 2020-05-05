package com.memastick.backmem.tokens.service;

import com.memastick.backmem.errors.exception.TokenWalletException;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.security.component.OauthData;
import com.memastick.backmem.tokens.api.TokenWalletAPI;
import com.memastick.backmem.tokens.constant.TokenType;
import com.memastick.backmem.tokens.entity.TokenWallet;
import com.memastick.backmem.tokens.repository.TokenWalletRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;
import java.util.function.BiConsumer;


@Service
@AllArgsConstructor
public class TokenWalletService {

    private final OauthData oauthData;
    private final TokenWalletRepository tokenWalletRepository;

    public void have(TokenType type) {
        Memetick memetick = oauthData.getCurrentMemetick();
        this.have(type, memetick);
    }

    public void have(TokenType type, Memetick memetick) {
        TokenWallet tokenWallet = tokenWalletRepository.findByMemetickId(memetick.getId());
        this.have(type, tokenWallet);
    }

    public void have(TokenType type, TokenWallet tokenWallet) {
        HashMap<TokenType, Integer> wallet = getWallet(tokenWallet);
        if (wallet.get(type) <= 0) throw new TokenWalletException();
    }

    public void take(TokenType type) {
        Memetick memetick = oauthData.getCurrentMemetick();
        this.take(type, memetick);
    }

    public void take(TokenType type, Memetick memetick) {
        TokenWallet tokenWallet = tokenWalletRepository.findByMemetickId(memetick.getId());
        this.take(type, tokenWallet);
    }

    public void take(TokenType type, TokenWallet tokenWallet) {
        HashMap<TokenType, Integer> wallet = getWallet(tokenWallet);
        Integer count = wallet.get(type);

        if (count <= 0) throw new TokenWalletException();
        var setWallet = setWallet();

        setWallet.get(type).accept(tokenWallet, count - 1);
        tokenWalletRepository.save(tokenWallet);
    }

    public TokenWalletAPI read(Memetick memetick) {
        return read(memetick.getId());
    }

    public TokenWalletAPI read(UUID memetickId) {
        return new TokenWalletAPI(wallet(memetickId));
    }

    public HashMap<TokenType, Integer> wallet(TokenWallet tokenWallet) {
        return getWallet(tokenWallet);
    }

    public HashMap<TokenType, Integer> wallet(Memetick memetick) {
        return wallet(memetick.getId());
    }

    public HashMap<TokenType, Integer> wallet(UUID memetickId) {
        return getWallet(tokenWalletRepository.findByMemetickId(memetickId));
    }

    public HashMap<TokenType, Integer> getWallet(TokenWallet tokenWallet) {
        HashMap<TokenType, Integer> wallet = new HashMap<>();

        wallet.put(TokenType.TUBE, tokenWallet.getTube());
        wallet.put(TokenType.SCOPE, tokenWallet.getScope());
        wallet.put(TokenType.MUTAGEN, tokenWallet.getMutagen());
        wallet.put(TokenType.CROSSOVER, tokenWallet.getCrossover());
        wallet.put(TokenType.ANTIBIOTIC, tokenWallet.getAntibiotic());

        return wallet;
    }

    public HashMap<TokenType, BiConsumer<TokenWallet, Integer>> setWallet() {
        HashMap<TokenType, BiConsumer<TokenWallet, Integer>> setter = new HashMap<>();

        setter.put(TokenType.TUBE, TokenWallet::setTube);
        setter.put(TokenType.SCOPE, TokenWallet::setScope);
        setter.put(TokenType.MUTAGEN, TokenWallet::setMutagen);
        setter.put(TokenType.CROSSOVER, TokenWallet::setCrossover);
        setter.put(TokenType.ANTIBIOTIC, TokenWallet::setAntibiotic);

        return setter;
    }

    public void generateWallet(Memetick memetick) {
        TokenWallet wallet = new TokenWallet();
        wallet.setMemetickId(memetick.getId());
        tokenWalletRepository.save(wallet);
    }
}
