package com.memastick.backmem.evolution.service;

import com.memastick.backmem.main.constant.GlobalConstant;
import com.memastick.backmem.main.dto.EPI;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memes.repository.MemeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@RequiredArgsConstructor
public class EvolveService {

    private final MemeRepository memeRepository;

    public EPI computeEPI() {
        return new EPI(
            this.computeEvolution(),
            this.computePopulation(),
            this.computeIndividuation()
        );
    }

    public long computeEvolution() {
        return DAYS.between(GlobalConstant.START_EVOLVE, LocalDate.now(ZoneOffset.UTC));
    }

    public long computePopulation() {
        return LocalTime.now(ZoneOffset.UTC).getHour() + 1;
    }

    public long computeIndividuation() {
        return memeRepository.countByEvolutionAndPopulation(computeEvolution(), computePopulation()).orElse(0L);
    }
}
