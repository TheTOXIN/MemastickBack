package com.memastick.backmem.battle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BattleMemberPreviewDTO {

    private UUID memberId;
    private String memeUrl;
}
