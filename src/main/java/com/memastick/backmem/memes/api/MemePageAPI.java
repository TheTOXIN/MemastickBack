package com.memastick.backmem.memes.api;

import com.memastick.backmem.evolution.constant.EvolveStep;
import com.memastick.backmem.memes.dto.MemeLikeStateDTO;
import com.memastick.backmem.memetick.api.MemetickPreviewAPI;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemePageAPI {

    private MemeAPI meme;
    private MemeLikeStateDTO likes;
    private MemetickPreviewAPI memetick;
    private EvolveStep step;
}
