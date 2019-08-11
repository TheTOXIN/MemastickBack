package com.memastick.backmem.memetick.service;

import com.memastick.backmem.evolution.service.EvolveMemeService;
import com.memastick.backmem.main.util.TimeUtil;
import com.memastick.backmem.memecoin.entity.BlockCoin;
import com.memastick.backmem.memecoin.repository.BlockCoinRepository;
import com.memastick.backmem.memecoin.service.MemeCoinService;
import com.memastick.backmem.memes.service.MemeCellService;
import com.memastick.backmem.memetick.api.CellAPI;
import com.memastick.backmem.memetick.api.MemetickInventoryAPI;
import com.memastick.backmem.memetick.api.PickaxeAPI;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.entity.MemetickInventory;
import com.memastick.backmem.memetick.repository.MemetickInventoryRepository;
import com.memastick.backmem.memetick.view.MemetickInventoryView;
import com.memastick.backmem.security.component.OauthData;
import com.memastick.backmem.tokens.entity.TokenWallet;
import com.memastick.backmem.tokens.repository.TokenWalletRepository;
import com.memastick.backmem.tokens.service.TokenWalletService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.memastick.backmem.main.constant.GlobalConstant.MAX_NONCE;
import static com.memastick.backmem.main.constant.GlobalConstant.PICKAXE_HOURS;

@Service
@AllArgsConstructor
public class MemetickInventoryService {

    private final MemetickInventoryRepository inventoryRepository;
    private final TokenWalletRepository tokenWalletRepository;
    private final TokenWalletService tokenWalletService;
    private final OauthData oauthData;
    private final EvolveMemeService evolveMemeService;
    private final MemeCoinService coinService;
    private final MemeCellService cellService;
    private final BlockCoinRepository blockCoinRepository;

    public MemetickInventoryAPI readAll() {
        Memetick memetick = oauthData.getCurrentMemetick();
        MemetickInventory inventory = inventoryRepository.findByMemetick(memetick);

        return new MemetickInventoryAPI(
            coinService.balance(memetick),
            memetick.getCookies(),
            cellService.checkState(inventory),
            inventory.isAllowance(),
            tokenWalletService.wallet(memetick)
        );
    }

    public CellAPI readStateCell() {
        return new CellAPI(
            cellService.stateCell(),
            evolveMemeService.computeEPI()
        );
    }

    public long countItems(Memetick memetick) {
        long count = 0;

        MemetickInventoryView view = inventoryRepository.findInventoryView(memetick);

        if (view.isAllowance()) count++;
        if (cellService.checkState(view)) count++;

        return count;
    }

    public PickaxeAPI getPickaxe() {
        Memetick memetick = oauthData.getCurrentMemetick();

        PickaxeAPI pickaxe = new PickaxeAPI();

        MemetickInventory inventory = inventoryRepository.findByMemetick(memetick);
        BlockCoin block = blockCoinRepository.findByMemetick(memetick);

        LocalDateTime pickaxeTime = inventory.getPickaxeTime();
        LocalDateTime nowTime = LocalDateTime.now();

        if (block.getNonce() <= MAX_NONCE) {
            pickaxe.setToken(inventory.getPickaxeToken());
        } else if (TimeUtil.isExpire(pickaxeTime)) {
            pickaxe.setToken(newPickaxe(inventory));
        }

        pickaxe.setHave(pickaxe.getToken() != null);

        if (!pickaxe.isHave()) pickaxe.setTime(TimeUtil.minusTime(pickaxeTime, nowTime));

        return pickaxe;
    }

    private UUID newPickaxe(MemetickInventory inventory) {
        inventory.setPickaxeTime(LocalDateTime.now().plusHours(PICKAXE_HOURS));
        inventory.setPickaxeToken(UUID.randomUUID());

        inventoryRepository.save(inventory);

        return inventory.getPickaxeToken();
    }

    public boolean checkPickaxe(UUID token) {
        return inventoryRepository.pickaxeByToken(token) != null;
    }

    public void generateInventory(Memetick memetick) {
        MemetickInventory inventory = new MemetickInventory();
        TokenWallet tokenWallet = new TokenWallet();

        inventory.setAllowance(false);
        inventory.setMemetick(memetick);

        tokenWalletRepository.save(tokenWallet);
        inventoryRepository.save(inventory);
    }
}
