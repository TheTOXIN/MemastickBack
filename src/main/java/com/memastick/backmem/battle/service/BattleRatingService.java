package com.memastick.backmem.battle.service;

import com.memastick.backmem.battle.api.BattleRatingAPI;
import com.memastick.backmem.battle.constant.BattleConst;
import com.memastick.backmem.battle.entity.BattleRating;
import com.memastick.backmem.battle.repository.BattleRatingRepository;
import com.memastick.backmem.main.util.JpaUtil;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.mapper.MemetickMapper;
import com.memastick.backmem.memotype.constant.MemotypeRarity;
import com.memastick.backmem.security.component.OauthData;
import lombok.AllArgsConstructor;
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
        Map<Integer, BattleRating> rating = getRating();

        for (int i = 0; i < BattleConst.RATING_SIZE; i++) {
            BattleRatingAPI api = new BattleRatingAPI();

            api.setPosition(i);
            api.setPresent(MemotypeRarity.findByPosition(i));

            Optional.ofNullable(rating.get(i)).ifPresent(r -> {
                api.setMemetick(memetickMapper.toPreviewDTO(r.getMemetick()));
                api.setScore(r.getScore());
                api.setDays(r.leftDays());
                api.setExist(true);
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
            int position = battleRatingRepository.findMyPosition(memetick.getId()) - 1;

            result.setPosition(position);
            result.setScore(r.getScore());
            result.setDays(r.leftDays());
            result.setExist(true);
        });

        return result;
    }

    public Map<Integer, BattleRating> getRating() {
        List<BattleRating> top = battleRatingRepository
            .findAll(JpaUtil.ratingPage())
            .stream()
            .filter(br -> br.getScore() >= 0)
            .collect(Collectors.toList());

        return IntStream.range(0, top.size())
            .boxed()
            .collect(Collectors.toMap(Function.identity(), top::get));
    }

    public void generate(Memetick memetick, int pvp) {
        BattleRating rating = battleRatingRepository
            .findByMemetick(memetick)
            .orElse(new BattleRating(memetick));

        int score = Math.max(0, rating.getScore() + pvp);
        rating.setScore(score);

        battleRatingRepository.save(rating);
    }
}
