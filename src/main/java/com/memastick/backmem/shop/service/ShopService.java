package com.memastick.backmem.shop.service;

import com.memastick.backmem.memecoin.service.MemeCoinService;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.repository.MemetickRepository;
import com.memastick.backmem.security.component.OauthData;
import com.memastick.backmem.shop.constant.PriceConst;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShopService {

    private final OauthData oauthData;
    private final MemeCoinService coinService;
    private final MemetickRepository memetickRepository;

    public ShopService(
        OauthData oauthData,
        MemeCoinService coinService,
        MemetickRepository memetickRepository
    ) {
        this.oauthData = oauthData;
        this.coinService = coinService;
        this.memetickRepository = memetickRepository;
    }

    @Transactional
    public void cookies(int count) {
        if (count <= 0) return;
        Memetick memetick = oauthData.getCurrentMemetick();
        coinService.transaction(memetick, PriceConst.COOKIE.getValue() * count);
        memetick.setCookies(memetick.getCookies() + count);
        memetickRepository.save(memetick);
    }
}
