package com.memastick.backmem.tokens.api;

import com.memastick.backmem.tokens.constant.TokenType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenWalletAPI {

    private HashMap<TokenType, Integer> wallet = new HashMap<>();

}
