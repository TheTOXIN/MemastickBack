package com.memastick.backmem.memetick.api;

import com.memastick.backmem.memetick.dto.MemetickRankDTO;
import com.memastick.backmem.tokens.api.TokenWalletAPI;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemetickProfileAPI {

    private UUID id;
    private String nick;

    private boolean follow;
    private boolean online;

    private long memecoins;
    private int cookies;

    private MemetickRankDTO rank;
    private TokenWalletAPI tokens;
}
