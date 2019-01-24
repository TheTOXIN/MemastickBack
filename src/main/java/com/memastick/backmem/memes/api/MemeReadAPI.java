package com.memastick.backmem.memes.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemeReadAPI {

    private UUID id;
    private UUID fireId;
    private UUID memetickId;

}
