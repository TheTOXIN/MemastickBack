package com.memastick.backmem.security.api;

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
