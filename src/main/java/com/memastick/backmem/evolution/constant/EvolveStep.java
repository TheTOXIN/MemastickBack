package com.memastick.backmem.evolution.constant;

public enum  EvolveStep {

    NONE(0),
    BIRTH(1),
    SYNTHS(2),
    MUTATE(3),
    GENERATE(4),
    SURVIVAL(5);

    private int step;

    EvolveStep(int step) {
        this.step = step;
    }
}
