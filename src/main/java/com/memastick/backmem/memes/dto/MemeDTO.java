package com.memastick.backmem.memes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemeDTO {

    private UUID id;
    private String url;
    private ZonedDateTime creating;

}
