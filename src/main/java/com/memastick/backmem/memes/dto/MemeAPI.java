package com.memastick.backmem.memes.dto;

import com.memastick.backmem.evolution.constant.EvolveStep;
import com.memastick.backmem.main.dto.EPI;
import com.memastick.backmem.memes.constant.MemeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private EPI epi;
}
