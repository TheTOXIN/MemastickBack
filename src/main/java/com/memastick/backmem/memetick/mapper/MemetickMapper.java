package com.memastick.backmem.memetick.mapper;

import com.memastick.backmem.memetick.dto.MemetickPreviewDTO;
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
}
