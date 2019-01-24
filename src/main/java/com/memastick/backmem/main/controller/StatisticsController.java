package com.memastick.backmem.main.controller;

import com.memastick.backmem.main.api.StatisticsAPI;
import com.memastick.backmem.main.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @Autowired
    public StatisticsController(
        StatisticsService statisticsService
    ) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/memetick/{memetickId}")
    public StatisticsAPI statisticsByMemetick(@PathVariable("memetickId") UUID memetickId) {
        return statisticsService.byMemetick(memetickId);
    }

    @GetMapping("/global")
    public StatisticsAPI statisticsGlobal() {
        return statisticsService.global();
    }

}
