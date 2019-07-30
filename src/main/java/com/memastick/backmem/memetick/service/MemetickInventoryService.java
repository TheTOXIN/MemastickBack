package com.memastick.backmem.memetick.service;

import com.memastick.backmem.evolution.service.EvolveMemeService;
import com.memastick.backmem.memecoin.service.MemeCoinService;
import com.memastick.backmem.memetick.api.CellAPI;
import com.memastick.backmem.memetick.api.MemetickInventoryAPI;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.entity.MemetickInventory;
import com.memastick.backmem.memetick.repository.MemetickInventoryRepository;
import com.memastick.backmem.security.component.OauthData;
import com.memastick.backmem.tokens.entity.TokenWallet;
import com.memastick.backmem.tokens.repository.TokenWalletRepository;
import com.memastick.backmem.tokens.service.TokenAllowanceService;
import com.memastick.backmem.tokens.service.TokenWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static com.memastick.backmem.main.constant.GlobalConstant.CELL_GROWTH;
import static com.memastick.backmem.main.constant.GlobalConstant.CELL_SIZE;

@Service
public class MemetickInventoryService {

    private final MemetickInventoryRepository inventoryRepository;
    private final TokenWalletRepository tokenWalletRepository;
    private final TokenWalletService tokenWalletService;
    private final TokenAllowanceService tokenAllowanceService;
    private final OauthData oauthData;
    private final EvolveMemeService evolveMemeService;
    private final MemeCoinService coinService;

    @Autowired
    public MemetickInventoryService(
        MemetickInventoryRepository inventoryRepository,
        TokenWalletRepository tokenWalletRepository,
        TokenWalletService tokenWalletService,
        TokenAllowanceService tokenAllowanceService,
        OauthData oauthData,
        EvolveMemeService evolveMemeService,
        MemeCoinService coinService
    ) {
        this.inventoryRepository = inventoryRepository;
        this.tokenWalletRepository = tokenWalletRepository;
        this.tokenWalletService = tokenWalletService;
        this.tokenAllowanceService = tokenAllowanceService;
        this.oauthData = oauthData;
        this.evolveMemeService = evolveMemeService;
        this.coinService = coinService;
    }

    public MemetickInventoryAPI readAll() {
        Memetick memetick = oauthData.getCurrentMemetick();

        return new MemetickInventoryAPI(
            coinService.balance(memetick),
            memetick.getCookies(),
            this.checkState(),
            tokenAllowanceService.have(memetick),
            tokenWalletService.read(memetick).getWallet()
        );
    }

    public CellAPI readStateCell() {
        return new CellAPI(
            stateCell(),
            evolveMemeService.computeEPI()
        );
    }

    public boolean checkState() {
        return this.stateCell() == CELL_SIZE;
    }

    public boolean checkState(MemetickInventory inventory) {
        return this.stateCell(inventory) == CELL_SIZE;
    }

    public int stateCell() {
        Memetick memetick = oauthData.getCurrentMemetick();
        MemetickInventory inventory = inventoryRepository.findByMemetick(memetick);

        return stateCell(inventory);
    }

    public int stateCell(MemetickInventory inventory) {
        LocalDateTime cell = inventory.getCellCreating();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime end = cell.plusHours(CELL_GROWTH);

        if (end.isBefore(now)) return CELL_SIZE;

        long full = ChronoUnit.MINUTES.between(cell, end);
        long lift = ChronoUnit.MINUTES.between(now, end);

        return CELL_SIZE - Math.min((int) (100f / full * lift), CELL_SIZE);
    }

    public long countItems(Memetick memetick) {
        long count = 0;

        MemetickInventory inventory = inventoryRepository.findByMemetick(memetick);

        if (inventory.isAllowance()) count++;
        if (stateCell(inventory) == CELL_SIZE) count++;

        return count;
    }

    public void updateCell(Memetick memetick) {
        MemetickInventory inventory = inventoryRepository.findByMemetick(memetick);

        inventory.setCellCreating(LocalDateTime.now());
        inventory.setCellNotify(false);

        inventoryRepository.save(inventory);
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
