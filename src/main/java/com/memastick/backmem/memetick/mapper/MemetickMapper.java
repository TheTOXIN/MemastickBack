package com.memastick.backmem.memetick.mapper;

import com.memastick.backmem.memetick.api.MemetickAPI;
import com.memastick.backmem.memetick.dto.MemetickPreviewDTO;
import com.memastick.backmem.memetick.dto.MemetickRatingDTO;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.setting.service.SettingFollowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class MemetickMapper {

    private final SettingFollowerService settingFollowerService;

    @Autowired
    public MemetickMapper(
        @Lazy SettingFollowerService settingFollowerService
    ) {
        this.settingFollowerService = settingFollowerService;
    }

    public MemetickPreviewDTO toPreviewDTO(Memetick memetick) {
        return new MemetickPreviewDTO(
            memetick.getId(),
            memetick.getNick()
        );
    }

    public MemetickAPI toMemetickAPI(Memetick memetick) {
        return new MemetickAPI(
            memetick.getId(),
            memetick.getNick(),
            settingFollowerService.follow(memetick)
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
