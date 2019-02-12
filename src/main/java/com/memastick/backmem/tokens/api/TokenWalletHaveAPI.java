package com.memastick.backmem.tokens.api;

import com.memastick.backmem.tokens.constant.TokenType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenWalletHaveAPI {

    private TokenType type;

}
