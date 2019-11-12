package com.memastick.backmem.security.service;

import com.memastick.backmem.main.util.ValidationUtil;
import com.memastick.backmem.security.api.RegistrationAPI;
import com.memastick.backmem.security.constant.SecurityStatus;
import com.memastick.backmem.security.entity.InviteCode;
import com.memastick.backmem.security.repository.InviteCodeRepository;
import com.memastick.backmem.user.entity.User;
import com.memastick.backmem.user.repository.UserRepository;
import com.memastick.backmem.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final InviteCodeService inviteCodeService;
    private final InviteCodeRepository inviteCodeRepository;

    @Transactional
    public SecurityStatus registration(RegistrationAPI request) {
        SecurityStatus status = validateRegistration(request);
        if (!status.equals(SecurityStatus.SUCCESSFUL)) return status;

        InviteCode invite = inviteCodeRepository.findByCode(request.getInvite()).orElse(null);
        if (invite == null || invite.isTake()) return SecurityStatus.INVITE;

        User user = userService.generateUser(request, invite);
        if (userRepository.findById(user.getId()).isEmpty()) return SecurityStatus.ERROR;

        inviteCodeService.take(invite);

        return status;
    }

    private SecurityStatus validateRegistration(RegistrationAPI request) {
        if (userRepository.findByLogin(request.getLogin()).isPresent()) return SecurityStatus.LOGIN_EXIST;
        if (!ValidationUtil.checkLogin(request.getLogin())) return SecurityStatus.LOGIN_INVALID;

        if (!ValidationUtil.checkPassword(request.getPassword())) return SecurityStatus.PASSWORD_WEAK;
        if (!request.getPassword().equals(request.getPasswordRepeat())) return SecurityStatus.PASSWORD_REPEAT;

        return SecurityStatus.SUCCESSFUL;
    }
}
