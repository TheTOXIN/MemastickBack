package com.memastick.backmem.invite.api;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "Z")
    private ZoneOffset zone;

}
