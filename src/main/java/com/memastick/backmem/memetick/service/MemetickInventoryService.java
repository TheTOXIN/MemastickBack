package com.memastick.backmem.memetick.service;

import com.memastick.backmem.errors.exception.CellSmallException;
import com.memastick.backmem.evolution.service.EvolveService;
import com.memastick.backmem.memecoin.service.MemeCoinService;
import com.memastick.backmem.memecoin.service.PickaxeService;
import com.memastick.backmem.memes.service.MemeCellService;
import com.memastick.backmem.memetick.api.CellAPI;
import com.memastick.backmem.memetick.api.MemetickInventoryAPI;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.entity.MemetickInventory;
import com.memastick.backmem.memetick.repository.MemetickInventoryRepository;
import com.memastick.backmem.memetick.view.CellInventoryView;
import com.memastick.backmem.memetick.view.MemetickInventoryView;
import com.memastick.backmem.security.component.OauthData;
import com.memastick.backmem.tokens.service.TokenWalletService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class MemetickInventoryService {

    private final MemetickInventoryRepository inventoryRepository;
    private final TokenWalletService tokenWalletService;
    private final OauthData oauthData;
    private final MemeCoinService coinService;
    private final MemeCellService cellService;
    private final PickaxeService pickaxeService;
    private final EvolveService evolveService;

    @Transactional(readOnly = true)
    public MemetickInventoryAPI readAll() {
        Memetick memetick = oauthData.getCurrentMemetick();
        MemetickInventory inventory = inventoryRepository.findByMemetick(memetick);

        return new MemetickInventoryAPI(
            coinService.balance(memetick),
            memetick.getCookies(),
            cellService.checkState(inventory),
            inventory.isAllowance(),
            pickaxeService.have(inventory),
            tokenWalletService.wallet(memetick)
        );
    }

    public CellAPI readStateCell() {
        Memetick memetick = oauthData.getCurrentMemetick();
        CellInventoryView inventory = inventoryRepository.findCellInventoryView(memetick);

        return new CellAPI(
            cellService.stateCell(inventory),
            cellService.currentCombo(inventory),
            evolveService.computeEPI()
        );
    }

    public void checkHaveCell() {
        if (!cellService.checkState()) {
            throw new CellSmallException();
        }
    }

    public long countItems(Memetick memetick) {
        long count = 0;

        MemetickInventoryView view = inventoryRepository.findInventoryView(memetick);

        if (view.isAllowance()) count++;
        if (cellService.checkState(view)) count++;
        if (pickaxeService.have(view.getPickaxeCreating())) count++;

        return count;
    }

    public void generateInventory(Memetick memetick) {
        MemetickInventory inventory = new MemetickInventory();

        inventory.setMemetick(memetick);

        inventoryRepository.save(inventory);
    }
}
