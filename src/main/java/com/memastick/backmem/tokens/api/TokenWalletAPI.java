package com.memastick.backmem.tokens.api;

import com.memastick.backmem.tokens.constant.TokenType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenWalletAPI {

    private Map<TokenType, Integer> wallet = new HashMap<>();

}
