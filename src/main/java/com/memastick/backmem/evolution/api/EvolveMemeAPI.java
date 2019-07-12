package com.memastick.backmem.evolution.api;

import com.memastick.backmem.evolution.constant.EvolveStep;
import com.memastick.backmem.main.dto.EPI;
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
    private EPI epi;

    private EvolveStep step;
    private boolean immunity;
    private int adaptation;

    private LocalTime nextTimer;
}
