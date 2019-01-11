package com.memastick.backmem.security.service;

import com.memastick.backmem.main.util.ValidationUtil;
import com.memastick.backmem.security.api.RegistrationAPI;
import com.memastick.backmem.security.constant.RegistrationStatus;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    public RegistrationStatus registration(RegistrationAPI request) {
        if (!ValidationUtil.validLogin(request.getLogin())) return RegistrationStatus.LOGIN;
        if (!ValidationUtil.isEmail(request.getEmail())) return RegistrationStatus.EMAIL;
        if (!ValidationUtil.checkPassword(request.getPassword())) return RegistrationStatus.PASSWORD;
        if (!request.getPassword().equals(request.getPasswordRepeat())) return RegistrationStatus.REPEAT;

        return RegistrationStatus.SUCCESSFUL;
    }

}
