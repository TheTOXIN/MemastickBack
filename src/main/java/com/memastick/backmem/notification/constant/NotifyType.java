package com.memastick.backmem.notification.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum NotifyType {

    DNA(false, false, true),
    CELL(false, true, true),
    MEME(true, false, false),
    TOKEN(true, false, true),
    CREATING(true, true, true),
    ALLOWANCE(false, true, true);

    @Getter boolean bell;
    @Getter boolean push;
    @Getter boolean web;

}
