package com.memastick.backmem.memetick.service;

import com.memastick.backmem.base.AbstractEntity;
import com.memastick.backmem.memes.constant.MemeType;
import com.memastick.backmem.memes.repository.MemeRepository;
import com.memastick.backmem.memetick.api.MemetickRatingAPI;
import com.memastick.backmem.memetick.constant.MemetickRatingFilter;
import com.memastick.backmem.memetick.dto.MemetickRatingDTO;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.mapper.MemetickMapper;
import com.memastick.backmem.memetick.repository.MemetickRepository;
import com.memastick.backmem.security.component.OauthData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MemetickRatingService {

    private Map<MemetickRatingFilter, Function<Memetick, Long>> mapFilter = new HashMap<>();

    private final MemetickRepository memetickRepository;
    private final OauthData oauthData;
    private final MemetickMapper memetickMapper;
    private final MemeRepository memeRepository;

    @Autowired
    public MemetickRatingService(
        MemetickRepository memetickRepository,
        OauthData oauthData,
        MemetickMapper memetickMapper,
        MemeRepository memeRepository
    ) {
        this.memetickRepository = memetickRepository;
        this.oauthData = oauthData;
        this.memetickMapper = memetickMapper;
        this.memeRepository = memeRepository;

        this.initFilter();
    }

    public MemetickRatingAPI rating(MemetickRatingFilter filter) {
        List<Memetick> memeticks = memetickRepository.findAll();

        Map<UUID, Long> rateMap = memeticks
            .stream()
            .collect(Collectors.toMap(Memetick::getId, m -> mapFilter.get(filter).apply(m)));

        memeticks.sort(Comparator.comparing(m -> 0 - rateMap.get(m.getId())));

        Map<UUID, Integer> posMap = memeticks
            .stream()
            .collect(Collectors.toMap(AbstractEntity::getId, memeticks::indexOf));

        Memetick memetick = oauthData.getCurrentMemetick();
        MemetickRatingDTO me = memetickMapper.toRatingDTO(
            memetick,
            rateMap.get(memetick.getId()),
            posMap.get(memetick.getId())
        );

        List<MemetickRatingDTO> top = memeticks
            .stream()
            .limit(10)
            .map(m -> memetickMapper.toRatingDTO(m, rateMap.get(m.getId()), posMap.get(m.getId())))
            .collect(Collectors.toList());

        return new MemetickRatingAPI(top, me);
    }

    private void initFilter() {
        this.mapFilter.put(MemetickRatingFilter.DNA, Memetick::getDna);
        this.mapFilter.put(MemetickRatingFilter.IND, m -> memeRepository.countByMemetickIdAndType(m.getId(), MemeType.INDV).orElse(0L));
        this.mapFilter.put(MemetickRatingFilter.CHR, m -> memeRepository.sumChromosomeByMemetickId(m.getId()).orElse(0L));
    }
}
