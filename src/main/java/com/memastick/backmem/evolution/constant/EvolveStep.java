package com.memastick.backmem.evolution.constant;

import com.memastick.backmem.tokens.constant.TokenType;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;

public enum EvolveStep {

    ADAPTATION(TokenType.TUBE, 1),
    FITNESS(TokenType.SCOPE, 2),
    MUTATION(TokenType.MUTAGEN, 3),
    CROSSING(TokenType.CROSSOVER, 4),
    SURVIVAL(TokenType.ANTIBIOTIC, 5);

    @Getter
    private TokenType token;

    @Getter
    private int number;

    EvolveStep(TokenType token, int number) {
        this.token = token;
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
