package com.memastick.backmem.memetick.dto;

import com.memastick.backmem.memetick.api.MemetickPreviewAPI;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemetickRatingDTO {

    private MemetickPreviewAPI preview;
    private long rate;
    private int pos;
}
