package com.memastick.backmem.memetick.service;

import com.memastick.backmem.memetick.api.RankTokenAPI;
import com.memastick.backmem.memetick.api.RankTypeAPI;
import com.memastick.backmem.memetick.constant.MemetickRankType;
import com.memastick.backmem.tokens.service.TokenAllowanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.memastick.backmem.memetick.constant.MemetickRankConst.MAX_LVL;

@Service
@RequiredArgsConstructor
public class MemetickRankInfoService {

    private final MemetickRankService memetickRankService;
    private final TokenAllowanceService tokenAllowanceService;

    @Cacheable(value = "rankTypes")
    public List<RankTypeAPI> getRankTypes() {
        return Arrays.stream(MemetickRankType.values())
            .map(this::getRankType)
            .collect(Collectors.toList());
    }

    private RankTypeAPI getRankType(MemetickRankType type) {
        return new RankTypeAPI(
            type.getLvl(),
            memetickRankService.computeDna(type.getLvl()),
            type.getName()
        );
    }

    @Cacheable(value = "rankTokens")
    public List<RankTokenAPI> getRankTokens() {
         return IntStream.range(0, MAX_LVL)
             .mapToObj(this::getRankToken)
             .collect(Collectors.toList());
    }

    private RankTokenAPI getRankToken(int lvl) {
        return new RankTokenAPI(
            lvl,
            tokenAllowanceService.compute(lvl)
        );
    }
}
