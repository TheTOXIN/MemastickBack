package com.memastick.backmem.memetick.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeNickAPI {

    private String nick;
    private boolean force;
}
