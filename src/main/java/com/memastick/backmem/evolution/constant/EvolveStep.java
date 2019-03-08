package com.memastick.backmem.evolution.constant;

import lombok.Getter;

import java.util.Arrays;

public enum EvolveStep {

    BIRTH(0),
    SURVIVAL(1);

    @Getter
    private int step;

    EvolveStep(int step) {
        this.step = step;
    }

    public static EvolveStep find(int step) {
        return Arrays
            .stream(EvolveStep.values())
            .filter(step1 -> step1.getStep() == step)
            .findFirst().orElse(null);
    }

}
