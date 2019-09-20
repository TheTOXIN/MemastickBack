package com.memastick.backmem.main.util;

import com.memastick.backmem.battle.constant.BattleConst;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class JpaUtil {

    public static Pageable makePage(Pageable pageable, Sort sort) {
        return PageRequest.of(
            pageable.getPageNumber(),
            pageable.getPageSize(),
            sort
        );
    }

    public static Pageable ratingPage() {
        return PageRequest.of(
            0,
            BattleConst.RATING_SIZE,
            Sort.by(Sort.Order.desc(("score")))
        );
    }
}
