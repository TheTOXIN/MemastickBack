package com.memastick.backmem.notification.constant;

import com.memastick.backmem.tokens.constant.TokenType;

import java.util.HashMap;
import java.util.Map;

public class NotifyText {

    public static final Map<TokenType, String> TOKEN_TEXT = new HashMap<>() {{
        put(TokenType.TUBE, "Мем адаптировали в популяции");
        put(TokenType.SCOPE, "Мем был оценен токеном");
        put(TokenType.MUTAGEN, "Мем мутировали комментарием");
        put(TokenType.CROSSOVER, "Мем скрестили с другим");
        put(TokenType.ANTIBIOTIC, "Мем был иммунизирован");
    }};
}
