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
        long next = computeDna(lvl + 1);
        long left = next - dna;
        int percent = (int) (100 * dna / left);

        MemetickRankType rank = rankMap.getOrDefault(lvl, RANK_SUPER);

        return new MemetickRankDTO(lvl, dna, next, left, percent, rank.getName());
    }

    private int computeLvl(long dna) {
        return (int) (Math.sqrt(dna) / LVL_COF);
    }

    private long computeDna(int lvl) {
        return (long) (Math.pow(LVL_COF, 2) * Math.pow(lvl, 2));
    }
}
