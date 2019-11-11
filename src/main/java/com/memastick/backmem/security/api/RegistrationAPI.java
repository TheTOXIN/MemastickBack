package com.memastick.backmem.security.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationAPI {

    private String invite;
    private String login;
    private String password;
    private String passwordRepeat;
}
