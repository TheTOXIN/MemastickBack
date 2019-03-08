package com.memastick.backmem.memes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemeLikeStateDTO {

    private long likes;
    private boolean myLike;
    private int myChromosomes;

}
