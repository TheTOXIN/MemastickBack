package com.memastick.backmem.memetick.api;

import com.memastick.backmem.tokens.constant.TokenType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemetickInventoryAPI {

    private long memecoins;

    private boolean cell;
    private boolean allowance;

    private Map<TokenType, Integer> wallet = new HashMap<>();
}
