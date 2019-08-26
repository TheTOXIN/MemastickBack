package com.memastick.backmem.notification.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum NotifyType {

    DNA(false, false, true),
    CELL(false, true, true),
    MEME(true, false, false),
    TOKEN(true, false, true),
    ADMIN(true, true, true),
    CREATING(true, true, true),
    ALLOWANCE(false, true, true),
    MEME_DAY(true, false, false),
    MEME_COIN(false, false, true),
    BATTLE_REQUEST(true, true, true),
    BATTLE_RESPONSE(true, true, true);

    @Getter boolean bell;
    @Getter boolean push;
    @Getter boolean web;
}
