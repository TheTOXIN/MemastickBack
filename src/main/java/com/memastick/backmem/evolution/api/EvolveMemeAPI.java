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
    private int adaptation;

    private EvolveStep step;
    private long population;
    private int chance;
    private boolean immunity;

}
