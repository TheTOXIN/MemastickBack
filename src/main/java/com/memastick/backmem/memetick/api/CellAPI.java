package com.memastick.backmem.memetick.api;

import com.memastick.backmem.main.dto.EPI;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CellAPI {

    private int state;
    private EPI epi;
}
