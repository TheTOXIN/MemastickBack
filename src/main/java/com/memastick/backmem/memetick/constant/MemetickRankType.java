package com.memastick.backmem.memetick.constant;

import lombok.Data;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.memastick.backmem.memetick.constant.MemetickRankConst.MAX_LVL;

public enum MemetickRankType {

    RANK_0(0, "Нуб"),
    RANK_1(5, "Ученик"),
    RANK_2(10, "Студент"),
    RANK_3(15, "Ассистент"),
    RANK_4(20, "Специалист"),
    RANK_5(25, "Доцент"),
    RANK_6(30, "Кандидат"),
    RANK_7(35, "Проффесор"),
    RANK_8(40, "Магистр"),
    RANK_9(45, "Доктор"),

    RANK_SUPER(MAX_LVL, "Сверх-разум");

    @Getter
    private int lvl;

    @Getter
    private String name;

    MemetickRankType(int lvl, String name) {
        this.lvl = lvl;
        this.name = name;
    }

    public static Map<Integer, MemetickRankType> getRankMap() {
        var result = new HashMap<Integer, MemetickRankType>();

        List<MemetickRankType> ranks = Arrays.stream(MemetickRankType.values())
            .sorted(Comparator.comparing(MemetickRankType::getLvl))
            .collect(Collectors.toList());

        for (int i = 0; i < ranks.size() - 1; i++) {
            MemetickRankType curr = ranks.get(i);
            MemetickRankType next = ranks.get(i + 1);

            int currLvl = curr.getLvl();
            int nextLvl = next.getLvl();

            IntStream.range(currLvl, nextLvl).forEach(lvl -> result.put(lvl, curr));
        }

        return result;
    }
}
