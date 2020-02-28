package com.memastick.backmem.memetick.service;

import com.memastick.backmem.memetick.constant.MemetickRankType;
import com.memastick.backmem.memetick.dto.MemetickRankDTO;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.security.component.OauthData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.memastick.backmem.memetick.constant.MemetickRankType.RANK_SUPER;

@Service
@RequiredArgsConstructor
public class MemetickRankService {

    private Map<Integer, MemetickRankType> rankMap = new HashMap<>();

    private static final int LVL_COF = 10;

    private final OauthData oauthData;

    public MemetickRankDTO myRank() {
        if (rankMap.isEmpty()) rankMap = MemetickRankType.getRankMap();

        Memetick memetick = oauthData.getCurrentMemetick();
        long dna = memetick.getDna();

        int lvl = computeLvl(dna);
        MemetickRankType rank = rankMap.getOrDefault(lvl, RANK_SUPER);

        return new MemetickRankDTO(lvl, dna, rank.getName());
    }

    private int computeLvl(long dna) {
        return (int) Math.sqrt(dna) / LVL_COF;
    }
}
