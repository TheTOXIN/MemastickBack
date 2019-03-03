package com.memastick.backmem.tokens.service;

import com.memastick.backmem.main.util.MathUtil;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.entity.MemetickInventory;
import com.memastick.backmem.memetick.repository.MemetickInventoryRepository;
import com.memastick.backmem.security.service.SecurityService;
import com.memastick.backmem.tokens.api.TokenWalletAPI;
import com.memastick.backmem.tokens.constant.TokenType;
import com.memastick.backmem.tokens.repository.TokenWalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TokenAllowanceService {

    private final SecurityService securityService;
    private final TokenWalletService tokenWalletService;
    private final MemetickInventoryRepository inventoryRepository;
    private final TokenWalletRepository tokenWalletRepository;

    @Autowired
    public TokenAllowanceService(
            SecurityService securityService,
            TokenWalletService tokenWalletService,
            MemetickInventoryRepository inventoryRepository,
            TokenWalletRepository tokenWalletRepository
    ) {
        this.securityService = securityService;
        this.tokenWalletService = tokenWalletService;
        this.inventoryRepository = inventoryRepository;
        this.tokenWalletRepository = tokenWalletRepository;
    }

    public TokenWalletAPI take() {
        Memetick memetick = securityService.getCurrentMemetick();
        MemetickInventory inventory = inventoryRepository.findByMemetick(memetick);

        if (!inventory.isAllowance()) return new TokenWalletAPI();
        inventory.setAllowance(false);

        var tokenWallet = inventory.getTokenWallet();
        var allowance = myAllowance(memetick);

        var wallet = tokenWalletService.getWallet(tokenWallet);
        var setter = tokenWalletService.setWallet();

        allowance.forEach((type, count) -> wallet.merge(type, count, (a, b) -> a + b));
        wallet.forEach((type, count) -> setter.get(type).accept(tokenWallet, count));

        tokenWalletRepository.save(tokenWallet);
        inventoryRepository.save(inventory);

        return new TokenWalletAPI(allowance);
    }

    public boolean have() {
        Memetick memetick = securityService.getCurrentMemetick();
        MemetickInventory inventory = inventoryRepository.findByMemetick(memetick);

        return inventory.isAllowance();
    }

    private Map<TokenType, Integer> myAllowance(Memetick memetick) {
        return Map.of(
                TokenType.CREATING, 1,
                TokenType.SELECTION,  MathUtil.randBool() ? 1 : 0
        );
    }
}
