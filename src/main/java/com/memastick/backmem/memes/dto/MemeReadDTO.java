package com.memastick.backmem.memes.dto;

import com.memastick.backmem.evolution.constant.EvolveStep;
import com.memastick.backmem.memes.constant.MemeFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemeReadDTO {

    private MemeFilter filter; 
    private EvolveStep step; 
    private UUID memetickId;
}
