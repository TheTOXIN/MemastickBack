package com.memastick.backmem.memes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemeLikeStateDTO {

    private boolean myLike;
    private int myChromosomes;
    private boolean firstChromosome;
}
