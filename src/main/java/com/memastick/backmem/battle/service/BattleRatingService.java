package com.memastick.backmem.battle.service;

import com.memastick.backmem.battle.api.BattleRatingAPI;
import com.memastick.backmem.battle.constant.BattleConst;
import com.memastick.backmem.battle.entity.BattleRating;
import com.memastick.backmem.battle.repository.BattleRatingRepository;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.mapper.MemetickMapper;
import com.memastick.backmem.security.component.OauthData;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
public class BattleRatingService {

    private final BattleRatingRepository battleRatingRepository;
    private final MemetickMapper memetickMapper;
    private final OauthData oauthData;

    public List<BattleRatingAPI> mainRating() {
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

            api.setPosition(i);

            Optional.ofNullable(map.get(i)).ifPresent(r -> {
                api.setScore(r.getScore());
                api.setExist(true);
                api.setMemetick(memetickMapper.toPreviewDTO(r.getMemetick()));
            });

            result.add(api);
        }

        return result;
    }

    public BattleRatingAPI myRating() {
        BattleRatingAPI result = new BattleRatingAPI();

        Memetick memetick = oauthData.getCurrentMemetick();

        result.setMemetick(memetickMapper.toPreviewDTO(memetick));

        battleRatingRepository.findByMemetick(memetick).ifPresent((r) -> {
            result.setScore(r.getScore());
            result.setExist(true);
        });

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
