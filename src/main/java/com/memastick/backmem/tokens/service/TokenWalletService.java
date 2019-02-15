package com.memastick.backmem.tokens.service;

import com.memastick.backmem.errors.exception.TokenWalletException;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.entity.MemetickInventory;
import com.memastick.backmem.memetick.repository.MemetickInventoryRepository;
import com.memastick.backmem.security.service.SecurityService;
import com.memastick.backmem.tokens.api.TokenWalletAPI;
import com.memastick.backmem.tokens.constant.TokenType;
import com.memastick.backmem.tokens.entity.TokenWallet;
import com.memastick.backmem.tokens.repository.TokenWalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.function.BiConsumer;


@Service
public class TokenWalletService {

    private final SecurityService securityService;
    private final MemetickInventoryRepository inventoryRepository;
    private final TokenWalletRepository tokenWalletRepository;

    @Autowired
    public TokenWalletService(
        SecurityService securityService,
        MemetickInventoryRepository inventoryRepository,
        TokenWalletRepository tokenWalletRepository
    ) {
        this.securityService = securityService;
        this.inventoryRepository = inventoryRepository;
        this.tokenWalletRepository = tokenWalletRepository;
    }

    public void have(TokenType type) {
        Memetick memetick = securityService.getCurrentMemetick();
        have(type, memetick);
    }

    public void have(TokenType type, Memetick memetick) {
        HashMap<TokenType, Integer> wallet = wallet(memetick);
        if (wallet.get(type) <= 0) throw new TokenWalletException();
    }

    public TokenWalletAPI my() {
        Memetick memetick = securityService.getCurrentMemetick();
        HashMap<TokenType, Integer> wallet = wallet(memetick);

        return new TokenWalletAPI(wallet);
    }

    public void take(TokenType type, Memetick memetick) {
        MemetickInventory inventory = inventoryRepository.findByMemetick(memetick);
        TokenWallet tokenWallet = inventory.getTokenWallet();

        HashMap<TokenType, Integer> wallet = getWallet(tokenWallet);
        Integer count = wallet.get(type);

        if (count <= 0) throw new TokenWalletException();
        var setWallet = setWallet();

        setWallet.get(type).accept(tokenWallet, count - 1);
        tokenWalletRepository.save(tokenWallet);
    }

    public HashMap<TokenType, Integer> wallet(Memetick memetick) {
        MemetickInventory inventory = inventoryRepository.findByMemetick(memetick);
        TokenWallet tokenWallet = inventory.getTokenWallet();

        return getWallet(tokenWallet);
    }

    public HashMap<TokenType, Integer> getWallet(TokenWallet tokenWallet) {
        HashMap<TokenType, Integer> wallet = new HashMap<>();

        wallet.put(TokenType.CREATING, tokenWallet.getCreating());
        wallet.put(TokenType.FITNESS, tokenWallet.getFitness());
        wallet.put(TokenType.MUTATION, tokenWallet.getMutation());
        wallet.put(TokenType.CROSSOVER, tokenWallet.getCrossover());
        wallet.put(TokenType.SELECTION, tokenWallet.getSelection());

        return wallet;
    }

    public HashMap<TokenType, BiConsumer<TokenWallet, Integer>> setWallet() {
        HashMap<TokenType, BiConsumer<TokenWallet, Integer>> setter = new HashMap<>();

        setter.put(TokenType.CREATING, TokenWallet::setCreating);
        setter.put(TokenType.FITNESS, TokenWallet::setFitness);
        setter.put(TokenType.MUTATION, TokenWallet::setMutation);
        setter.put(TokenType.CROSSOVER, TokenWallet::setCrossover);
        setter.put(TokenType.SELECTION, TokenWallet::setSelection);

        return setter;
    }
}
