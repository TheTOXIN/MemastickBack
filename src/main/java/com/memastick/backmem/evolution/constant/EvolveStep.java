package com.memastick.backmem.evolution.constant;

import lombok.Getter;

import java.util.Arrays;

public enum EvolveStep {

    ADAPTATION(0),
    FITNESS(1),
    MUTATION(2),
    CROSSING(3),
    SURVIVAL(4);

    @Getter
    private int number;

    EvolveStep(int number) {
        this.number = number;
    }

    public static EvolveStep find(int number) {
        return Arrays
            .stream(EvolveStep.values())
            .filter(e -> e.getNumber() == number)
            .findFirst()
            .orElse(null);
    }
}
