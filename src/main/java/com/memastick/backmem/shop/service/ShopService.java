package com.memastick.backmem.shop.service;

import com.memastick.backmem.errors.consts.ErrorCode;
import com.memastick.backmem.errors.exception.ShopException;
import com.memastick.backmem.memecoin.service.MemeCoinService;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.repository.MemetickRepository;
import com.memastick.backmem.security.component.OauthData;
import com.memastick.backmem.tokens.constant.TokenType;
import com.memastick.backmem.tokens.entity.TokenWallet;
import com.memastick.backmem.tokens.repository.TokenWalletRepository;
import com.memastick.backmem.tokens.service.TokenWalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

import static com.memastick.backmem.main.constant.ValidConstant.MAX_TOKEN;
import static com.memastick.backmem.shop.constant.PriceConst.COOKIE;
import static com.memastick.backmem.shop.constant.PriceConst.TOKENS;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final OauthData oauthData;
    private final MemeCoinService coinService;
    private final MemetickRepository memetickRepository;
    private final TokenWalletRepository tokenWalletRepository;
    private final TokenWalletService tokenWalletService;

    @Transactional
    public void cookies(int count) {
        if (count <= 0) return;
        Memetick memetick = oauthData.getCurrentMemetick();

        int price = COOKIE.getPrice() * count;
        coinService.transaction(memetick, price);

        memetick.setCookies(memetick.getCookies() + count);
        memetickRepository.save(memetick);
    }

    @Transactional
    public void tokens(TokenType type, int count) {
        if (count <= 0) return;
        Memetick memetick = oauthData.getCurrentMemetick();

        TokenWallet tokenWallet = tokenWalletRepository.findByMemetickId(memetick.getId());
        var wallet = tokenWalletService.wallet(tokenWallet);

        int countTokens = wallet.get(type) + count;
        if (countTokens > MAX_TOKEN) throw new ShopException(ErrorCode.TOO_MUCH);

        int priceTokens = type.getLvl() * TOKENS.getPrice() * count;
        coinService.transaction(memetick, priceTokens);

        tokenWalletService.setWallet().get(type).accept(tokenWallet, countTokens);
        tokenWalletRepository.save(tokenWallet);
    }
}
