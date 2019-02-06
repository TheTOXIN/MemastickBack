package com.memastick.backmem.person.dto;

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
