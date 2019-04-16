package com.memastick.backmem.memetick.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemetickRatingDTO {

    private MemetickPreviewDTO preview;
    private long rate;
    private int pos;

}
