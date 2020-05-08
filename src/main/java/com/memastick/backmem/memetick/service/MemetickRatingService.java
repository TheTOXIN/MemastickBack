package com.memastick.backmem.memetick.service;

import com.memastick.backmem.base.AbstractEntity;
import com.memastick.backmem.main.constant.GlobalConstant;
import com.memastick.backmem.memes.constant.MemeType;
import com.memastick.backmem.memes.repository.MemeRepository;
import com.memastick.backmem.memetick.api.MemetickRatingAPI;
import com.memastick.backmem.memetick.constant.MemetickRatingFilter;
import com.memastick.backmem.memetick.dto.MemetickRatingDTO;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.mapper.MemetickMapper;
import com.memastick.backmem.memetick.repository.MemetickRepository;
import com.memastick.backmem.security.component.OauthData;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.memastick.backmem.memes.constant.MemeType.INDV;
import static com.memastick.backmem.memetick.constant.MemetickRatingFilter.*;

@Service
@RequiredArgsConstructor
public class MemetickRatingService {

    private final Map<MemetickRatingFilter, Function<Memetick, Long>> mapFilter = new HashMap<>();

    private final MemetickRepository memetickRepository;
    private final MemetickMapper memetickMapper;
    private final MemeRepository memeRepository;
    private final OauthData oauthData;

    @PostConstruct
    public void initFilter() {
        mapFilter.put(DNA, Memetick::getDna);
        mapFilter.put(CHR, m -> memeRepository.sumChromosomeByMemetickId(m.getId()).orElse(0L));
        mapFilter.put(IND, m -> memeRepository.countByMemetickIdAndType(m.getId(), INDV).orElse(0L));
    }

    @Transactional(readOnly = true)
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
            .limit(GlobalConstant.MEMETICK_RATING)
            .map(m -> memetickMapper.toRatingDTO(m, rateMap.get(m.getId()), posMap.get(m.getId())))
            .collect(Collectors.toList());

        return new MemetickRatingAPI(top, me);
    }
}
