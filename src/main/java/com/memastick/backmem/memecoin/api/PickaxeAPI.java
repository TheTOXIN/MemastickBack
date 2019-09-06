package com.memastick.backmem.memecoin.api;

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

    private UUID token;
    private boolean have;
    private LocalTime receipt;
}
