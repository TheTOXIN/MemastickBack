package com.memastick.backmem.memes.api;

import com.memastick.backmem.main.dto.EPI;
import com.memastick.backmem.memes.constant.MemeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemeAPI {

    private UUID id;

    private String url;
    private String text;

    private MemeType type;

    private int likes;
    private int chromosomes;

    private ZonedDateTime creating;

    private EPI epi;
}
