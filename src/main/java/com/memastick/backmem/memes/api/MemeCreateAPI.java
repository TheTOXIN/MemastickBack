package com.memastick.backmem.memes.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemeCreateAPI {

    private UUID fireId;
    private String url;
    private String text;
}
