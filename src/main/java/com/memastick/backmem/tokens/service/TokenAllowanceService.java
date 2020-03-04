package com.memastick.backmem.tokens.service;

import com.memastick.backmem.memecoin.service.MemeCoinService;
import com.memastick.backmem.memetick.dto.MemetickRankDTO;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.entity.MemetickInventory;
import com.memastick.backmem.memetick.repository.MemetickInventoryRepository;
import com.memastick.backmem.memetick.service.MemetickRankService;
import com.memastick.backmem.security.component.OauthData;
import com.memastick.backmem.shop.constant.PriceConst;
import com.memastick.backmem.tokens.api.TokenWalletAPI;
import com.memastick.backmem.tokens.constant.TokenType;
import com.memastick.backmem.tokens.repository.TokenWalletRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.memastick.backmem.main.constant.ValidConstant.MAX_TOKEN;

@Service
@AllArgsConstructor
public class TokenAllowanceService {

    private final OauthData oauthData;
    private final TokenWalletService tokenWalletService;
    private final MemetickInventoryRepository inventoryRepository;
    private final TokenWalletRepository tokenWalletRepository;
    private final MemeCoinService coinService;
    private final MemetickRankService rankService;

    public TokenWalletAPI take() {
        Memetick memetick = oauthData.getCurrentMemetick();
        MemetickInventory inventory = inventoryRepository.findByMemetick(memetick);

        if (!inventory.isAllowance()) return new TokenWalletAPI(emptyAllowance());
        inventory.setAllowance(false);

        var allowance = myAllowance(memetick);
        var tokenWallet = tokenWalletRepository.findByMemetickId(memetick.getId());

        var wallet = tokenWalletService.getWallet(tokenWallet);
        var setter = tokenWalletService.setWallet();

        allowance.forEach((type, count) -> wallet.merge(type, count, (a, b) -> Math.min(a + b, MAX_TOKEN)));
        wallet.forEach((type, count) -> setter.get(type).accept(tokenWallet, count));

        inventoryRepository.save(inventory);
        tokenWalletRepository.save(tokenWallet);

        return new TokenWalletAPI(allowance);
    }

    public void make() {
        Memetick memetick = oauthData.getCurrentMemetick();
        MemetickInventory inventory = inventoryRepository.findByMemetick(memetick);
        coinService.transaction(memetick, PriceConst.ALLOWANCE.getValue());
        inventory.setAllowance(true);
        inventoryRepository.save(inventory);
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
        var result = new HashMap<TokenType, Integer>();

        MemetickRankDTO dto = rankService.rank(memetick);

        int lvl = dto.getLvl();
        int rare = lvl / MAX_TOKEN;

        Arrays.asList(TokenType.values()).forEach(token -> {
            if (token.ordinal() == rare) {
                result.put(token, lvl % MAX_TOKEN + 1);
            } else if (token.ordinal() < rare) {
                result.put(token, MAX_TOKEN);
            } else {
                result.put(token, 0);
            }
        });

        return result;
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
