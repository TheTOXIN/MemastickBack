package com.memastick.backmem.memetick.service;

import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.entity.MemetickInventory;
import com.memastick.backmem.memetick.repository.MemetickInventoryRepository;
import com.memastick.backmem.tokens.constant.TokenType;
import com.memastick.backmem.tokens.entity.TokenWallet;
import com.memastick.backmem.tokens.repository.TokenWalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MemetickInventoryService {

    private final MemetickInventoryRepository inventoryRepository;
    private final TokenWalletRepository tokenWalletRepository;

    @Autowired
    public MemetickInventoryService(
        MemetickInventoryRepository inventoryRepository,
        TokenWalletRepository tokenWalletRepository
    ) {
        this.inventoryRepository = inventoryRepository;
        this.tokenWalletRepository = tokenWalletRepository;
    }

    public void updateAllowance(Memetick memetick) {
        MemetickInventory inventory = inventoryRepository.findByMemetick(memetick);

        if (inventory.isAllowance()) return;
        inventory.setAllowance(true);

        inventoryRepository.save(inventory);
    }

    public Map<TokenType, Integer> myAllowance(Memetick memetick) {
        return Map.of(
            TokenType.CREATING, 1
        );
    }

    public void generateInventory(Memetick memetick) {
        MemetickInventory inventory = new MemetickInventory();
        TokenWallet tokenWallet = new TokenWallet();

        inventory.setTokenWallet(tokenWallet);
        inventory.setAllowance(false);
        inventory.setMemetick(memetick);

        tokenWalletRepository.save(tokenWallet);
        inventoryRepository.save(inventory);
    }
}
