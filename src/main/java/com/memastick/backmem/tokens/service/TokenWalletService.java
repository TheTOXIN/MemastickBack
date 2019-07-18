package com.memastick.backmem.tokens.service;

import com.memastick.backmem.errors.exception.TokenWalletException;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.entity.MemetickInventory;
import com.memastick.backmem.memetick.repository.MemetickInventoryRepository;
import com.memastick.backmem.memetick.service.MemetickService;
import com.memastick.backmem.security.component.OauthData;
import com.memastick.backmem.tokens.api.TokenWalletAPI;
import com.memastick.backmem.tokens.constant.TokenType;
import com.memastick.backmem.tokens.entity.TokenWallet;
import com.memastick.backmem.tokens.repository.TokenWalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;
import java.util.function.BiConsumer;


@Service
public class TokenWalletService {

    private final OauthData oauthData;
    private final MemetickInventoryRepository inventoryRepository;
    private final TokenWalletRepository tokenWalletRepository;
    private final MemetickService memetickService;

    @Autowired
    public TokenWalletService(
        OauthData oauthData,
        MemetickInventoryRepository inventoryRepository,
        TokenWalletRepository tokenWalletRepository,
        MemetickService memetickService
    ) {
        this.oauthData = oauthData;
        this.inventoryRepository = inventoryRepository;
        this.tokenWalletRepository = tokenWalletRepository;
        this.memetickService = memetickService;
    }

    public void have(TokenType type) {
        Memetick memetick = oauthData.getCurrentMemetick();
        have(type, memetick);
    }

    public void have(TokenType type, Memetick memetick) {
        HashMap<TokenType, Integer> wallet = wallet(memetick);
        if (wallet.get(type) <= 0) throw new TokenWalletException();
    }

    public TokenWalletAPI read() {
        Memetick currentMemetick = oauthData.getCurrentMemetick();
        return read(currentMemetick);
    }

    public TokenWalletAPI read(UUID memetickId) {
        Memetick memetick = memetickService.findById(memetickId);
        return read(memetick);
    }

    public TokenWalletAPI read(Memetick memetick) {
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
}
