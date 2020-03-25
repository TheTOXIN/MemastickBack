package com.memastick.backmem.memetick.service;

import com.memastick.backmem.memetick.constant.MemetickRankType;
import com.memastick.backmem.memetick.dto.MemetickRankDTO;
import com.memastick.backmem.memetick.api.RankTokenAPI;
import com.memastick.backmem.memetick.api.RankTypeAPI;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.security.component.OauthData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.memastick.backmem.memetick.constant.MemetickRankConst.LVL_COF;
import static com.memastick.backmem.memetick.constant.MemetickRankType.RANK_SUPER;

@Service
@RequiredArgsConstructor
public class MemetickRankService {

    private Map<Integer, MemetickRankType> rankMap = new HashMap<>();

    private final OauthData oauthData;

    public MemetickRankDTO rank() {
        return rank(
            oauthData.getCurrentMemetick()
        );
    }

    public MemetickRankDTO rank(Memetick memetick) {
        if (rankMap.isEmpty()) rankMap = MemetickRankType.getRankMap();

        long dna = memetick.getDna();
        int lvl = computeLvl(dna);
        long next = computeDna(lvl + 1);
        long left = next - dna;
        int percent = (int) (100 * dna / next);

        MemetickRankType rank = rankMap.getOrDefault(lvl, RANK_SUPER);

        return new MemetickRankDTO(lvl, dna, next, left, percent, rank.getName());
    }

    int computeLvl(long dna) {
        return (int) (Math.sqrt(dna) / LVL_COF);
    }

    long computeDna(int lvl) {
        return (long) (Math.pow(LVL_COF, 2) * Math.pow(lvl, 2));
    }
}
