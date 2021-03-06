package com.memastick.backmem.tokens.service;

import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.entity.MemetickInventory;
import com.memastick.backmem.memetick.repository.MemetickInventoryRepository;
import com.memastick.backmem.memetick.service.MemetickRankService;
import com.memastick.backmem.security.component.OauthData;
import com.memastick.backmem.tokens.api.TokenWalletAPI;
import com.memastick.backmem.tokens.constant.TokenType;
import com.memastick.backmem.tokens.repository.TokenWalletRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.memastick.backmem.main.constant.ValidConstant.MAX_TOKEN;
import static com.memastick.backmem.main.util.MathUtil.rand;

@Service
@AllArgsConstructor
public class TokenAllowanceService {

    private final OauthData oauthData;
    private final TokenWalletService tokenWalletService;
    private final MemetickInventoryRepository inventoryRepository;
    private final TokenWalletRepository tokenWalletRepository;
    private final MemetickRankService rankService;

    public TokenWalletAPI take() {
        Memetick memetick = oauthData.getCurrentMemetick();
        MemetickInventory inventory = inventoryRepository.findByMemetick(memetick);

        if (!inventory.isAllowance()) return new TokenWalletAPI(emptyAllowance());
        inventory.setAllowance(false);

        var allowance = compute(rankService.rank(memetick).getLvl(), true);
        var tokenWallet = tokenWalletRepository.findByMemetickId(memetick.getId());

        var wallet = tokenWalletService.getWallet(tokenWallet);
        var setter = tokenWalletService.setWallet();

        allowance.forEach((type, count) -> wallet.merge(type, count, (a, b) -> Math.min(a + b, MAX_TOKEN)));
        wallet.forEach((type, count) -> setter.get(type).accept(tokenWallet, count));

        inventoryRepository.save(inventory);
        tokenWalletRepository.save(tokenWallet, memetick);

        return new TokenWalletAPI(allowance);
    }

    public Map<TokenType, Integer> compute(int lvl, boolean random) {
        var result = new HashMap<TokenType, Integer>();

        int rare = lvl / MAX_TOKEN;
        Arrays.asList(TokenType.values()).forEach(token -> {
            int chance = 0;
            if (token.ordinal() == rare) {
                chance = lvl % MAX_TOKEN + 1;
            } else if (token.ordinal() < rare) {
                chance =  MAX_TOKEN;
            }

            int count;
            if (random && chance > 0) {
                count = rand(1, chance);
            } else {
                count = chance;
            }

            result.put(token, count);
        });

        return result;
    }

    public boolean have() {
        return have(oauthData.getCurrentMemetick());
    }

    public boolean have(Memetick memetick) {
        return have(inventoryRepository.findByMemetick(memetick));
    }

    public boolean have(MemetickInventory inventory) {
        return inventory.isAllowance();
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
