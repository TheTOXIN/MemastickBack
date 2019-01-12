package com.memastick.backmem.security.service;

import com.memastick.backmem.security.entity.InviteCode;
import com.memastick.backmem.main.util.ValidationUtil;
import com.memastick.backmem.person.entity.User;
import com.memastick.backmem.person.repository.UserRepository;
import com.memastick.backmem.person.service.UserService;
import com.memastick.backmem.security.api.RegistrationAPI;
import com.memastick.backmem.security.constant.RegistrationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SecurityService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final InviteCodeService inviteCodeService;

    @Autowired
    public SecurityService(
        UserRepository userRepository,
        InviteCodeService inviteCodeService,
        UserService userService
    ) {
        this.userRepository = userRepository;
        this.inviteCodeService = inviteCodeService;
        this.userService = userService;
    }

    public RegistrationStatus registration(RegistrationAPI request) {
        RegistrationStatus status = validate(request);

        if (!status.equals(RegistrationStatus.SUCCESSFUL)) return status;

        InviteCode inviteCode = inviteCodeService.findByCode(request.getInvite());
        User user = userService.generateUser(request, inviteCode);

        if (userRepository.findById(user.getId()).isEmpty()) return RegistrationStatus.ERROR;

        inviteCodeService.take(request.getInvite());

        return status;
    }

    private RegistrationStatus validate(RegistrationAPI request) {
        if (userRepository.findByLogin(request.getLogin()).isPresent()) return RegistrationStatus.LOGIN_EXIST;
        if (userRepository.findByEmail(request.getEmail()).isPresent()) return RegistrationStatus.EMAIL_EXIST;

        if (!ValidationUtil.validLogin(request.getLogin())) return RegistrationStatus.LOGIN_INVALID;
        if (!ValidationUtil.isEmail(request.getEmail())) return RegistrationStatus.EMAIL_INVALID;

        if (!ValidationUtil.checkPassword(request.getPassword())) return RegistrationStatus.PASSWORD_WEAK;
        if (!request.getPassword().equals(request.getPasswordRepeat())) return RegistrationStatus.PASSWORD_REPEAT;

        if (!inviteCodeService.validInvite(request.getEmail(), request.getInvite())) return RegistrationStatus.INVITE;

        return RegistrationStatus.SUCCESSFUL;
    }

}
