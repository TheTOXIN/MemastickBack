package com.memastick.backmem.memes.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemeLikeAPI {

    private int likes;
    private int chromosomes;

    private boolean myLike;
    private int myChromosomes;

}
