package com.memastick.backmem.battle.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BattleRequestAPI {

    private UUID fromMeme;
    private UUID toMeme;
}
