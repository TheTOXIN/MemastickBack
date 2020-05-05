package com.memastick.backmem.main.api;


import com.memastick.backmem.memetick.api.MemetickPreviewAPI;
import com.memastick.backmem.memetick.dto.MemetickRankDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomeAPI {

    private MemetickPreviewAPI memetick;
    private MemetickRankDTO rank;

    private String message;

    private long day;
    private long memes;

    private LocalTime selectTimer;

    private boolean creedAgree;
}
