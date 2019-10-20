package com.memastick.backmem.security.service;

import com.memastick.backmem.main.util.ValidationUtil;
import com.memastick.backmem.security.api.RegistrationAPI;
import com.memastick.backmem.security.constant.SecurityStatus;
import com.memastick.backmem.security.entity.InviteCode;
import com.memastick.backmem.user.entity.User;
import com.memastick.backmem.user.repository.UserRepository;
import com.memastick.backmem.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final InviteCodeService inviteCodeService;

    @Transactional
    public SecurityStatus registration(RegistrationAPI request) {
        SecurityStatus status = validateRegistration(request);

        if (!status.equals(SecurityStatus.SUCCESSFUL)) return status;
        User user = userService.generateUser(request);

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
