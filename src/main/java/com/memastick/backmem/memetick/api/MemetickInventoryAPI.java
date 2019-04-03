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

    private Map<TokenType, Integer> wallet = new HashMap<>();
    private boolean allowance;

}
