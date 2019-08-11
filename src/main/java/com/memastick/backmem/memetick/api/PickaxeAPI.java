package com.memastick.backmem.memetick.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PickaxeAPI {

    private boolean have;
    private LocalTime time;
    private UUID token;
}
