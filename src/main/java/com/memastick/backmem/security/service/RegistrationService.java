package com.memastick.backmem.security.service;

import com.memastick.backmem.security.entity.InviteCode;
import com.memastick.backmem.main.util.ValidationUtil;
import com.memastick.backmem.person.entity.User;
import com.memastick.backmem.person.repository.UserRepository;
import com.memastick.backmem.person.service.UserService;
import com.memastick.backmem.security.api.RegistrationAPI;
import com.memastick.backmem.security.constant.SecurityStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RegistrationService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final InviteCodeService inviteCodeService;

    @Autowired
    public RegistrationService(
        UserRepository userRepository,
        InviteCodeService inviteCodeService,
        UserService userService
    ) {
        this.userRepository = userRepository;
        this.inviteCodeService = inviteCodeService;
        this.userService = userService;
    }

    public SecurityStatus registration(RegistrationAPI request) {
        SecurityStatus status = validateRegistration(request);

        if (!status.equals(SecurityStatus.SUCCESSFUL)) return status;

        InviteCode inviteCode = inviteCodeService.findByCode(request.getInvite());
        User user = userService.generateUser(request, inviteCode);

        if (userRepository.findById(user.getId()).isEmpty()) return SecurityStatus.ERROR;

        inviteCodeService.take(request.getInvite());

        return status;
    }

    private SecurityStatus validateRegistration(RegistrationAPI request) {
        if (userRepository.findByLogin(request.getLogin()).isPresent()) return SecurityStatus.LOGIN_EXIST;
        if (userRepository.findByEmail(request.getEmail()).isPresent()) return SecurityStatus.EMAIL_EXIST;

        if (!ValidationUtil.checkLogin(request.getLogin())) return SecurityStatus.LOGIN_INVALID;
        if (!ValidationUtil.checkEmail(request.getEmail())) return SecurityStatus.EMAIL_INVALID;

        if (!ValidationUtil.checkPassword(request.getPassword())) return SecurityStatus.PASSWORD_WEAK;
        if (!request.getPassword().equals(request.getPasswordRepeat())) return SecurityStatus.PASSWORD_REPEAT;

        if (!inviteCodeService.validInvite(request.getEmail(), request.getInvite())) return SecurityStatus.INVITE;

        return SecurityStatus.SUCCESSFUL;
    }

}
