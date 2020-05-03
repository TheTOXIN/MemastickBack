package com.memastick.backmem.memetick.view;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CellInventoryView {

    private int cellCombo;
    private LocalDateTime cellCreating;
}
