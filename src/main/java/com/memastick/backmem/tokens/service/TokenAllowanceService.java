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

import static com.memastick.backmem.main.constant.GlobalConstant.MAX_TOKEN;

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

        if (!inventory.isAllowance()) return new TokenWalletAPI(emptyAllowance());
        inventory.setAllowance(false);

        var tokenWallet = inventory.getTokenWallet();
        var allowance = myAllowance(memetick);

        var wallet = tokenWalletService.getWallet(tokenWallet);
        var setter = tokenWalletService.setWallet();

        allowance.forEach((type, count) -> wallet.merge(type, count, (a, b) -> Math.min(a + b, MAX_TOKEN)));
        wallet.forEach((type, count) -> setter.get(type).accept(tokenWallet, count));

        tokenWalletRepository.save(tokenWallet);
        inventoryRepository.save(inventory);

        return new TokenWalletAPI(allowance);
    }

    public boolean have() {
        Memetick memetick = securityService.getCurrentMemetick();
        return have(memetick);
    }

    public boolean have(Memetick memetick) {
        MemetickInventory inventory = inventoryRepository.findByMemetick(memetick);
        return inventory.isAllowance();
    }

    private Map<TokenType, Integer> myAllowance(Memetick memetick) {
        return Map.of(
            TokenType.TUBE, 1,
            TokenType.SCOPE, 0,
            TokenType.MUTAGEN, 0,
            TokenType.CROSSOVER, 0,
            TokenType.ANTIBIOTIC,  MathUtil.randBool() ? 1 : 0
        );
    }

    private Map<TokenType, Integer> emptyAllowance() {
        return Map.of(
            TokenType.TUBE, 0,
            TokenType.SCOPE, 0,
            TokenType.MUTAGEN, 0,
            TokenType.CROSSOVER, 0,
            TokenType.ANTIBIOTIC,  0
        );
    }
}
