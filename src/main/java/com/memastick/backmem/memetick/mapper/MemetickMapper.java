package com.memastick.backmem.memetick.mapper;

import com.memastick.backmem.memetick.api.MemetickAPI;
import com.memastick.backmem.memetick.dto.MemetickPreviewDTO;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.tokens.service.TokenAllowanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class MemetickMapper {

    private final TokenAllowanceService allowanceService;

    @Autowired
    public MemetickMapper(
        @Lazy TokenAllowanceService allowanceService
    ) {
        this.allowanceService = allowanceService;
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
            memetick.getDna(),
            allowanceService.have(memetick)
        );
    }
}
