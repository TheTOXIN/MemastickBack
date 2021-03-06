package com.memastick.backmem.main.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotifyCountAPI {

    private long countItems;
    private long countBells;
}
