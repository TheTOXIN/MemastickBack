package com.memastick.backmem.battle.api;

import com.memastick.backmem.memetick.api.MemetickPreviewAPI;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BattleRatingAPI {

    private MemetickPreviewAPI memetick;
    private boolean exist;
    private int position;
    private int score;
}
