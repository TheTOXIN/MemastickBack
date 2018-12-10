package com.memastick.backmem.invite.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.ZoneOffset;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class InviteAPI {

    @Email
    @NotNull
    private String email;

    private ZoneOffset zone;

}
