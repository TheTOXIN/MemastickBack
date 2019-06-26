package com.memastick.backmem.evolution.api;

import com.memastick.backmem.evolution.constant.EvolveStep;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvolveMemeAPI {

    private UUID memeId;
    private long population;

    private EvolveStep step;
    private boolean immunity;
    private int adaptation;

}
