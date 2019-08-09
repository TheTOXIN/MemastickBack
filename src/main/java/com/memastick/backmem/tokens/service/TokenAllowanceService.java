package com.memastick.backmem.tokens.service;

import com.memastick.backmem.main.util.MathUtil;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.entity.MemetickInventory;
import com.memastick.backmem.memetick.repository.MemetickInventoryRepository;
import com.memastick.backmem.security.component.OauthData;
import com.memastick.backmem.tokens.api.TokenWalletAPI;
import com.memastick.backmem.tokens.constant.TokenType;
import com.memastick.backmem.tokens.repository.TokenWalletRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.memastick.backmem.main.constant.GlobalConstant.MAX_TOKEN;

@Service
@AllArgsConstructor
public class TokenAllowanceService {

    private final OauthData oauthData;
    private final TokenWalletService tokenWalletService;
    private final MemetickInventoryRepository inventoryRepository;
    private final TokenWalletRepository tokenWalletRepository;

    public TokenWalletAPI take() {
        Memetick memetick = oauthData.getCurrentMemetick();
        MemetickInventory inventory = inventoryRepository.findByMemetick(memetick);

        if (!inventory.isAllowance()) return new TokenWalletAPI(emptyAllowance());
        inventory.setAllowance(false);

        var allowance = myAllowance(memetick);
        var tokenWallet = tokenWalletRepository.findByMemetick(memetick);

        var wallet = tokenWalletService.getWallet(tokenWallet);
        var setter = tokenWalletService.setWallet();

        allowance.forEach((type, count) -> wallet.merge(type, count, (a, b) -> Math.min(a + b, MAX_TOKEN)));
        wallet.forEach((type, count) -> setter.get(type).accept(tokenWallet, count));

        inventoryRepository.save(inventory);
        tokenWalletRepository.save(tokenWallet);

        return new TokenWalletAPI(allowance);
    }

    public boolean have() {
        return have(oauthData.getCurrentMemetick());
    }

    public boolean have(Memetick memetick) {
        return have( inventoryRepository.findByMemetick(memetick));
    }

    public boolean have(MemetickInventory inventory) {
        return inventory.isAllowance();
    }

    private Map<TokenType, Integer> myAllowance(Memetick memetick) {
        return Map.of(
            TokenType.TUBE, MathUtil.rand(TokenType.TUBE.getStep().getNumber() + 1) == 0 ? 1 : 0,
            TokenType.SCOPE, MathUtil.rand(TokenType.SCOPE.getStep().getNumber() + 1) == 0 ? 1 : 0,
            TokenType.MUTAGEN, MathUtil.rand(TokenType.MUTAGEN.getStep().getNumber() + 1) == 0 ? 1 : 0,
            TokenType.CROSSOVER, MathUtil.rand(TokenType.CROSSOVER.getStep().getNumber() + 1) == 0 ? 1 : 0,
            TokenType.ANTIBIOTIC,  MathUtil.rand(TokenType.ANTIBIOTIC.getStep().getNumber() + 1) == 0 ? 1 : 0
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
