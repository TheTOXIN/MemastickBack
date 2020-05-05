package com.memastick.backmem.memetick.api;

import com.memastick.backmem.tokens.api.TokenWalletAPI;
import com.memastick.backmem.tokens.constant.TokenType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RankTokenAPI {

    private int lvl;
    private Map<TokenType, Integer> tokens = new HashMap<>();
}
