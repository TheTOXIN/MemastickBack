package com.memastick.backmem.evolution.api;

import com.memastick.backmem.evolution.constant.EvolveStep;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvolveMemeAPI {

    private EvolveStep step;
    private long population;
    private float chanceSurvive;

}
