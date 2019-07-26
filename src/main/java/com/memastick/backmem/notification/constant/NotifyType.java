package com.memastick.backmem.notification.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum NotifyType {

    DNA(false, false, true),
    CELL(false, true, true),
    MEME(true, false, false),
    TOKEN(true, false, true),
    ADMIN(true, false, true),
    CREATING(true, true, true),
    ALLOWANCE(false, true, true),
    MEME_DAY(true, false, false),
    MEME_COIN(false, false, true);

    @Getter boolean bell;
    @Getter boolean push;
    @Getter boolean web;
}
