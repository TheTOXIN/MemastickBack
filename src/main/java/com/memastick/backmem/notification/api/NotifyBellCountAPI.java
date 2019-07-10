package com.memastick.backmem.notification.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotifyBellCountAPI {

    private long countItems;
    private long countBells;
}
