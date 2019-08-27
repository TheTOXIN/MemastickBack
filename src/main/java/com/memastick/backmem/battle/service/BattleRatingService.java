package com.memastick.backmem.battle.service;

import com.memastick.backmem.battle.api.BattleRatingAPI;
import com.memastick.backmem.battle.constant.BattleConst;
import com.memastick.backmem.battle.entity.BattleRating;
import com.memastick.backmem.battle.repository.BattleRatingRepository;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.mapper.MemetickMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
public class BattleRatingService {

    private final BattleRatingRepository battleRatingRepository;
    private final MemetickMapper memetickMapper;

    public List<BattleRatingAPI> rating() {
        List<BattleRatingAPI> result = new ArrayList<>();

        List<BattleRating> content = battleRatingRepository.findAll(
            PageRequest.of(
                0,
                BattleConst.RATING_SIZE,
                Sort.by(Sort.Order.desc(("score")))
            )
        ).getContent();

        Map<Integer, BattleRating> map = IntStream.range(0, content.size())
            .boxed()
            .collect(Collectors.toMap(Function.identity(), content::get));

        for (int i = 0; i < BattleConst.RATING_SIZE; i++) {
            BattleRatingAPI api = new BattleRatingAPI();

            BattleRating rating = map.get(i);

            if (rating != null) {
                api.setScore(rating.getScore());
                api.setMemetick(memetickMapper.toPreviewDTO(rating.getMemetick()));
            }

            api.setFree(rating == null);
            api.setPosition(i);

            result.add(api);
        }

        return result;
    }

    public void generate(Memetick memetick, int pvp) {
        BattleRating rating = battleRatingRepository
            .findByMemetick(memetick)
            .orElse(new BattleRating(memetick));

        rating.setScore(rating.getScore() + pvp);

        battleRatingRepository.save(rating);
    }
}
