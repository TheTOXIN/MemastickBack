package com.memastick.backmem.memotype.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemotypeSetAPI {

    private String name;
    private String description;
    private int size;

    private long count;
    private List<MemotypeAPI> memotypes;
}
