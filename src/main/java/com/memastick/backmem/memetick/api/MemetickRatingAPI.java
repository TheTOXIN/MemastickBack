package com.memastick.backmem.memetick.api;

import com.memastick.backmem.memetick.dto.MemetickRatingDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemetickRatingAPI {

    private List<MemetickRatingDTO> top = new ArrayList<>();
    private MemetickRatingDTO me;

}
