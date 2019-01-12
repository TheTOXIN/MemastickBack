package com.memastick.backmem.code.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class InviteCodeAPI {

    private String email;
    private String nick;

}
