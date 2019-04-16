package com.memastick.backmem.memetick.mapper;

import com.memastick.backmem.memetick.api.MemetickAPI;
import com.memastick.backmem.memetick.dto.MemetickPreviewDTO;
import com.memastick.backmem.memetick.dto.MemetickRatingDTO;
import com.memastick.backmem.memetick.entity.Memetick;
import org.springframework.stereotype.Service;

@Service
public class MemetickMapper {

    public MemetickPreviewDTO toPreviewDTO(Memetick memetick) {
        return new MemetickPreviewDTO(
            memetick.getId(),
            memetick.getNick()
        );
    }

    public MemetickAPI toMemetickAPI(Memetick memetick) {
        return new MemetickAPI(
            memetick.getId(),
            memetick.getNick()
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
