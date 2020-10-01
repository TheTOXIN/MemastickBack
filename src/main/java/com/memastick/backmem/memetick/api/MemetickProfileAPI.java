package com.memastick.backmem.memetick.api;

import com.memastick.backmem.tokens.api.TokenWalletAPI;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemetickProfileAPI  {

    private MemetickAPI memetick;

    private boolean follow;
    private boolean online;

    private long memecoins;

    private TokenWalletAPI tokens;
}
