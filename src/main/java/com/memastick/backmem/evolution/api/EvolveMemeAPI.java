package com.memastick.backmem.evolution.api;

import com.memastick.backmem.evolution.constant.EvolveStep;
import com.memastick.backmem.main.dto.EPI;
import com.memastick.backmem.memes.dto.MemeCommentDTO;
import com.memastick.backmem.memes.dto.MemeLohDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvolveMemeAPI {

    private UUID memeId;

    private EvolveStep step;
    private boolean immunity;
    private int adaptation;

    private boolean myMeme;
    private boolean canApplyToken;

    private EPI epi;

    private MemeLohDTO loh;
    private MemeCommentDTO comment;

    private LocalTime nextTimer;
}
