package com.memastick.backmem.memetick.mapper;

import com.memastick.backmem.memecoin.service.MemeCoinService;
import com.memastick.backmem.memetick.api.MemetickAPI;
import com.memastick.backmem.memetick.api.MemetickPreviewAPI;
import com.memastick.backmem.memetick.api.MemetickProfileAPI;
import com.memastick.backmem.memetick.dto.MemetickRatingDTO;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.service.MemetickRankService;
import com.memastick.backmem.security.service.SecurityService;
import com.memastick.backmem.setting.service.SettingFollowerService;
import com.memastick.backmem.tokens.service.TokenWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class MemetickMapper {

    private final SettingFollowerService settingFollowerService;
    private final SecurityService securityService;
    private final MemeCoinService memeCoinService;
    private final MemetickRankService memetickRankService;
    private final TokenWalletService tokenWalletService;

    @Autowired
    public MemetickMapper(
        @Lazy SettingFollowerService settingFollowerService,
        SecurityService securityService,
        @Lazy MemeCoinService memeCoinService,
        MemetickRankService memetickRankService,
        TokenWalletService tokenWalletService
    ) {
        this.settingFollowerService = settingFollowerService;
        this.securityService = securityService;
        this.memeCoinService = memeCoinService;
        this.memetickRankService = memetickRankService;
        this.tokenWalletService = tokenWalletService;
    }

    public MemetickAPI toAPI(Memetick memetick) {
        return new MemetickAPI(
            memetick.getId(),
            memetick.getNick(),
            memetick.getCookies(),
            memetickRankService.rank(memetick)
        );
    }

    public MemetickProfileAPI toProfileAPI(Memetick memetick) {
        return new MemetickProfileAPI(
            toAPI(memetick),
            settingFollowerService.follow(memetick),
            securityService.isOnline(memetick),
            memeCoinService.balance(memetick),
            tokenWalletService.read(memetick)
        );
    }

    public MemetickPreviewAPI toPreviewDTO(Memetick memetick) {
        return new MemetickPreviewAPI(
            memetick.getId(),
            memetick.getNick(),
            memetickRankService.computeLvl(memetick.getDna())
        );
    }

    public MemetickRatingDTO toRatingDTO(Memetick memetick, long rate, int pos) {
        return new MemetickRatingDTO(
            toPreviewDTO(memetick),
            rate,
            pos
        );
    }
}
