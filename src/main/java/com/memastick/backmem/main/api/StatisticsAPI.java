package com.memastick.backmem.main.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsAPI {

    private long individual;
    private long chromosome;
    private long dna;
}
