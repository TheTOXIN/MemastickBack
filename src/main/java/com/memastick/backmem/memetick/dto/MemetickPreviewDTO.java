package com.memastick.backmem.memetick.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemetickPreviewDTO {

    private UUID id;
    private String nick;

}
