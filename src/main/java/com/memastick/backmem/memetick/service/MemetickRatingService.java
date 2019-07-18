package com.memastick.backmem.memetick.service;

import com.memastick.backmem.base.entity.AbstractEntity;
import com.memastick.backmem.memes.constant.MemeType;
import com.memastick.backmem.memes.repository.MemeRepository;
import com.memastick.backmem.memetick.api.MemetickRatingAPI;
import com.memastick.backmem.memetick.constant.MemetickRatingFilter;
import com.memastick.backmem.memetick.dto.MemetickRatingDTO;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.mapper.MemetickMapper;
import com.memastick.backmem.memetick.repository.MemetickRepository;
import com.memastick.backmem.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MemetickRatingService {

    private Map<MemetickRatingFilter, Pair<LocalDate, MemetickRatingAPI>> cacheFilter = new HashMap<>();
    private Map<MemetickRatingFilter, Function<Memetick, Long>> mapFilter = new HashMap<>();

    private final MemetickRepository memetickRepository;
    private final SecurityService securityService;
    private final MemetickMapper memetickMapper;
    private final MemeRepository memeRepository;

    @Autowired
    public MemetickRatingService(
        MemetickRepository memetickRepository,
        SecurityService securityService,
        MemetickMapper memetickMapper,
        MemeRepository memeRepository
    ) {
        this.memetickRepository = memetickRepository;
        this.securityService = securityService;
        this.memetickMapper = memetickMapper;
        this.memeRepository = memeRepository;

        this.initFilter();
    }

    public MemetickRatingAPI rating(MemetickRatingFilter filter) {
        if (checkCache(filter)) return cacheFilter.get(filter).getSecond();

        List<Memetick> memeticks = memetickRepository.findAll();

        Map<UUID, Long> rateMap = memeticks
            .stream()
            .collect(Collectors.toMap(Memetick::getId, m -> mapFilter.get(filter).apply(m)));

        memeticks.sort(Comparator.comparing(m -> 0 - rateMap.get(m.getId())));

        Map<UUID, Integer> posMap = memeticks
            .stream()
            .collect(Collectors.toMap(AbstractEntity::getId, memeticks::indexOf));

        Memetick memetick = securityService.getCurrentMemetick();
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

        MemetickRatingAPI ratingAPI = new MemetickRatingAPI(top, me);

        cacheFilter.put(filter, Pair.of(LocalDate.now(), ratingAPI));

        return ratingAPI;
    }

    private boolean checkCache(MemetickRatingFilter filter) {
        Pair<LocalDate, MemetickRatingAPI> cache = cacheFilter.get(filter);

        if (cache == null) return false;

        return cache.getFirst().plusDays(1).isAfter(LocalDate.now());
    }

    private void initFilter() {
        this.mapFilter.put(MemetickRatingFilter.DNA, Memetick::getDna);
        this.mapFilter.put(MemetickRatingFilter.IND, m -> memeRepository.countByMemetickIdAndType(m.getId(), MemeType.INDV).orElse(0L));
        this.mapFilter.put(MemetickRatingFilter.CHR, m -> memeRepository.sumChromosomeByMemetickId(m.getId()).orElse(0L));
    }
}
