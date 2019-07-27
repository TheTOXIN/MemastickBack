package com.memastick.backmem.memetick.mapper;

import com.memastick.backmem.memecoin.service.MemeCoinService;
import com.memastick.backmem.memetick.api.MemetickAPI;
import com.memastick.backmem.memetick.api.MemetickPreviewAPI;
import com.memastick.backmem.memetick.dto.MemetickRatingDTO;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.setting.service.SettingFollowerService;
import com.memastick.backmem.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class MemetickMapper {

    private final SettingFollowerService settingFollowerService;
    private final UserService userService;
    private final MemeCoinService memeCoinService;

    @Autowired
    public MemetickMapper(
        @Lazy SettingFollowerService settingFollowerService,
        @Lazy UserService userService,
        @Lazy MemeCoinService memeCoinService
    ) {
        this.settingFollowerService = settingFollowerService;
        this.userService = userService;
        this.memeCoinService = memeCoinService;
    }

    public MemetickPreviewAPI toPreviewDTO(Memetick memetick) {
        return new MemetickPreviewAPI(
            memetick.getId(),
            memetick.getNick()
        );
    }

    public MemetickAPI toMemetickAPI(Memetick memetick) {
        return new MemetickAPI(
            memetick.getId(),
            memetick.getNick(),
            settingFollowerService.follow(memetick),
            userService.isOnline(memetick),
            memeCoinService.balance(memetick),
            memetick.getCookies()
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
