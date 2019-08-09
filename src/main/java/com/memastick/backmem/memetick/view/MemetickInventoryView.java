package com.memastick.backmem.memetick.view;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MemetickInventoryView {

    private boolean allowance;
    private LocalDateTime cellCreating;
}
