package com.memastick.backmem.memetick.service;

import com.memastick.backmem.main.constant.GlobalConstant;
import com.memastick.backmem.memetick.api.MemetickInventoryAPI;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.entity.MemetickInventory;
import com.memastick.backmem.memetick.repository.MemetickInventoryRepository;
import com.memastick.backmem.security.service.SecurityService;
import com.memastick.backmem.tokens.entity.TokenWallet;
import com.memastick.backmem.tokens.repository.TokenWalletRepository;
import com.memastick.backmem.tokens.service.TokenAllowanceService;
import com.memastick.backmem.tokens.service.TokenWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class MemetickInventoryService {

    private final MemetickInventoryRepository inventoryRepository;
    private final TokenWalletRepository tokenWalletRepository;
    private final TokenWalletService tokenWalletService;
    private final TokenAllowanceService tokenAllowanceService;
    private final SecurityService securityService;

    @Autowired
    public MemetickInventoryService(
        MemetickInventoryRepository inventoryRepository,
        TokenWalletRepository tokenWalletRepository,
        TokenWalletService tokenWalletService,
        TokenAllowanceService tokenAllowanceService,
        SecurityService securityService
    ) {
        this.inventoryRepository = inventoryRepository;
        this.tokenWalletRepository = tokenWalletRepository;
        this.tokenWalletService = tokenWalletService;
        this.tokenAllowanceService = tokenAllowanceService;
        this.securityService = securityService;
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

    public MemetickInventoryAPI readAll() {
        return new MemetickInventoryAPI(
            tokenWalletService.read().getWallet(),
            tokenAllowanceService.have(),
            this.stateCell() == 100
        );
    }

    public int stateCell() {
        Memetick memetick = securityService.getCurrentMemetick();
        MemetickInventory inventory = inventoryRepository.findByMemetick(memetick);

        LocalDateTime cell = inventory.getCellCreating();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime end = cell.plusDays(GlobalConstant.CELL_GROWTH);

        if (end.isBefore(now)) return 100;

        long full = ChronoUnit.MINUTES.between(cell, end);
        long lift = ChronoUnit.MINUTES.between(now, end);

        return 100 - Math.min((int) (100f / full * lift), 100);
    }
}
