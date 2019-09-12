package com.memastick.backmem.main.controller;

import com.memastick.backmem.main.api.StatisticsAPI;
import com.memastick.backmem.main.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/memetick/{memetickId}")
    public ResponseEntity<StatisticsAPI> statisticsByMemetick(
        @PathVariable("memetickId") UUID memetickId
    ) {
        return ResponseEntity.ok(
            statisticsService.byMemetick(memetickId)
        );
    }

    @GetMapping("/global")
    public ResponseEntity<StatisticsAPI> statisticsGlobal() {
        return ResponseEntity.ok()
            .cacheControl(CacheControl.maxAge(1, TimeUnit.DAYS))
            .body(statisticsService.global());
    }
}
