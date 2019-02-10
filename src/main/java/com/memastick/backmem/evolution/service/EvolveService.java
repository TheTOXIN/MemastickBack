package com.memastick.backmem.evolution.service;

import com.memastick.backmem.main.constant.GlobalConstant;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneOffset;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class EvolveService {

    public static long evolveDay() {
        return DAYS.between(
            GlobalConstant.START_EVOLVE,
            LocalDate.now(ZoneOffset.UTC)
        );
    }

}
