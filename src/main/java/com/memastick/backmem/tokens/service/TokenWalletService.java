package com.memastick.backmem.tokens.service;

import com.memastick.backmem.errors.exception.TokenWalletException;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.entity.MemetickInventory;
import com.memastick.backmem.memetick.repository.MemetickInventoryRepository;
import com.memastick.backmem.security.service.SecurityService;
import com.memastick.backmem.tokens.api.TokenWalletAPI;
import com.memastick.backmem.tokens.constant.TokenType;
import com.memastick.backmem.tokens.entity.TokenWallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;


@Service
public class TokenWalletService {

    private final SecurityService securityService;
    private final MemetickInventoryRepository inventoryRepository;

    @Autowired
    public TokenWalletService(
        SecurityService securityService,
        MemetickInventoryRepository inventoryRepository
    ) {
        this.securityService = securityService;
        this.inventoryRepository = inventoryRepository;
    }

    public void have(TokenType type) {
        Memetick memetick = securityService.getCurrentMemetick();
        HashMap<TokenType, Integer> wallet = wallet(memetick);

        if (wallet.get(type) <= 0) throw new TokenWalletException();
    }

    public TokenWalletAPI my() {
        Memetick memetick = securityService.getCurrentMemetick();
        HashMap<TokenType, Integer> wallet = wallet(memetick);

        return new TokenWalletAPI(wallet);
    }

    private HashMap<TokenType, Integer> wallet(Memetick memetick) {
        MemetickInventory inventory = inventoryRepository.findByMemetick(memetick);
        TokenWallet tokenWallet = inventory.getTokenWallet();

        HashMap<TokenType, Integer> wallet = new HashMap<>();

        wallet.put(TokenType.CREATING, tokenWallet.getCreating());
        wallet.put(TokenType.FITNESS, tokenWallet.getFitness());
        wallet.put(TokenType.MUTATION, tokenWallet.getMutation());
        wallet.put(TokenType.CROSSOVER, tokenWallet.getCrossover());
        wallet.put(TokenType.SELECTION, tokenWallet.getSelection());

        return wallet;
    }
}
