package com.memastick.backmem.memetick.api;

import com.memastick.backmem.memetick.dto.MemetickRankDTO;
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
    private int cookies;
    private MemetickRankDTO rank;
}
