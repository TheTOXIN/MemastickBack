package com.memastick.backmem.tokens.api;

import com.memastick.backmem.memes.dto.MemeLohDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenAcceptAPI {

    private MemeLohDTO loh = new MemeLohDTO();
}
