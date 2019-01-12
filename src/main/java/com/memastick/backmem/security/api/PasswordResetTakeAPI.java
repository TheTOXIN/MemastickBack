package com.memastick.backmem.security.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetTakeAPI {

    private String code;
    private String password;
    private String passwordRepeat;

}
