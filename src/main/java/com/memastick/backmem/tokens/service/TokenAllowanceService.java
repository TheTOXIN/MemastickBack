package com.memastick.backmem.tokens.service;

import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.repository.MemetickInventoryRepository;
import com.memastick.backmem.memetick.service.MemetickInventoryService;
import com.memastick.backmem.tokens.entity.TokenWallet;
import com.memastick.backmem.tokens.repository.TokenWalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
public class TokenAllowanceService {

    private final MemetickInventoryRepository inventoryRepository;
    private final MemetickInventoryService memetickInventoryService;
    private final TokenWalletService tokenWalletService;
    private final TokenWalletRepository tokenWalletRepository;

    @Autowired
    public TokenAllowanceService(
        MemetickInventoryRepository inventoryRepository,
        MemetickInventoryService memetickInventoryService,
        TokenWalletService tokenWalletService,
        TokenWalletRepository tokenWalletRepository
    ) {
        this.inventoryRepository = inventoryRepository;
        this.memetickInventoryService = memetickInventoryService;
        this.tokenWalletService = tokenWalletService;
        this.tokenWalletRepository = tokenWalletRepository;
    }

    @Scheduled(cron = "0 0 12 * * *", zone = "UTC")
    public void allowance() {
        inventoryRepository.findByAllowanceTrue().forEach(inventory -> {
            TokenWallet tokenWallet = inventory.getTokenWallet();
            Memetick memetick = inventory.getMemetick();

            var allowance = memetickInventoryService.myAllowance(memetick);
            var wallet = tokenWalletService.getWallet(tokenWallet);

            var setter = tokenWalletService.setWallet();
            inventory.setAllowance(false);

            allowance.forEach((type, count) -> wallet.merge(type, count, (a, b) -> a + b));
            wallet.forEach((type, count) -> setter.get(type).accept(tokenWallet, count));

            tokenWalletRepository.save(tokenWallet);
            inventoryRepository.save(inventory);
        });
    }
}
