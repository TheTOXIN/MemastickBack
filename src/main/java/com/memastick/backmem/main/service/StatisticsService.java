package com.memastick.backmem.main.service;

import com.memastick.backmem.evolution.service.EvolveMemeService;
import com.memastick.backmem.evolution.service.EvolveService;
import com.memastick.backmem.main.api.StatisticsAPI;
import com.memastick.backmem.main.facade.StatisticsFacade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class StatisticsService {

    private Map<Long, StatisticsAPI> globalCache;

    private final StatisticsFacade statisticsFacade;
    private final EvolveService evolveService;

    public StatisticsAPI byMemetick(UUID memetickId) {
        return parse(
            statisticsFacade.memetick(memetickId)
        );
    }

    public StatisticsAPI global() {
        return globalCache.computeIfAbsent(
            evolveService.computeEvolution(),
            p -> parse(statisticsFacade.global())
        );
    }

    private StatisticsAPI parse(List<BigDecimal> result) {
        BigDecimal i = result.get(0);
        BigDecimal c = result.get(1);
        BigDecimal d = result.get(2);

        return new StatisticsAPI(
            i != null ? i.longValue() : 0L,
            c != null ? c.longValue() : 0L,
            d != null ? d.longValue() : 0L
        );
    }
}
