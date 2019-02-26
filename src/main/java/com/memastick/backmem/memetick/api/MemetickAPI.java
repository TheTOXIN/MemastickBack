package com.memastick.backmem.memetick.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemetickAPI {

    private UUID id;
    private String nick;
    private long dna;

}
