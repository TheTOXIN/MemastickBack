package com.memastick.backmem.security.service;

import com.memastick.backmem.errors.consts.ErrorCode;
import com.memastick.backmem.errors.exception.EntityNotFoundException;
import com.memastick.backmem.errors.exception.TimeInException;
import com.memastick.backmem.errors.exception.TimeOutException;
import com.memastick.backmem.main.util.ValidationUtil;
import com.memastick.backmem.person.entity.User;
import com.memastick.backmem.person.repository.UserRepository;
import com.memastick.backmem.person.service.UserService;
import com.memastick.backmem.security.api.PasswordResetTakeAPI;
import com.memastick.backmem.security.constant.SecurityStatus;
import com.memastick.backmem.security.entity.PasswordReset;
import com.memastick.backmem.security.repository.PasswordResetRepository;
import com.memastick.backmem.sender.dto.EmailStatus;
import com.memastick.backmem.sender.service.SenderPasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordResetService {

    private final PasswordResetRepository passwordResetRepository;
    private final UserRepository userRepository;
    private final SenderPasswordResetService senderPasswordResetService;
    private final UserService userService;

    @Autowired
    public PasswordResetService(
        PasswordResetRepository passwordResetRepository,
        UserRepository userRepository,
        SenderPasswordResetService senderPasswordResetService,
        UserService userService
    ) {
        this.passwordResetRepository = passwordResetRepository;
        this.userRepository = userRepository;
        this.senderPasswordResetService = senderPasswordResetService;
        this.userService = userService;
    }

    public EmailStatus send(String email) {
        Optional<User> byEmail = userRepository.findByEmail(email);
        if (byEmail.isEmpty()) throw new EntityNotFoundException(User.class, "email");
        User user = byEmail.get();

        Optional<PasswordReset> byLogin = passwordResetRepository.findByLogin(user.getLogin());
        PasswordReset passwordReset;

        if (byLogin.isPresent()) {
            passwordReset = byLogin.get();
            checkTimeReset(passwordReset, ErrorCode.TIME_IN);
            updateCode(passwordReset);
        } else {
            passwordReset = generateReset(user);
        }

        return senderPasswordResetService.send(passwordReset, email);
    }

    public SecurityStatus take(PasswordResetTakeAPI request) {
        Optional<PasswordReset> byCode = passwordResetRepository.findByCode(request.getCode());
        if (byCode.isEmpty()) return SecurityStatus.INVITE;

        PasswordReset passwordReset = byCode.get();
        checkTimeReset(passwordReset, ErrorCode.TIME_OUT);

        if (!ValidationUtil.checkPassword(request.getPassword())) return SecurityStatus.PASSWORD_WEAK;
        if (!request.getPassword().equals(request.getPasswordRepeat())) return SecurityStatus.PASSWORD_REPEAT;

        userService.updatePassword(passwordReset.getLogin(), request.getPassword());
        wipeCode(passwordReset);

        return SecurityStatus.SUCCESSFUL;
    }

    private PasswordReset generateReset(User user) {
        PasswordReset passwordReset = new PasswordReset();

        passwordReset.setLogin(user.getLogin());
        passwordReset.setCode(makeResetCode());
        passwordReset.setTime(LocalDateTime.now().plusDays(1));

        return passwordResetRepository.save(passwordReset);
    }

    private void checkTimeReset(PasswordReset passwordReset, ErrorCode errorCode) {
        if (passwordReset.getTime().isAfter(LocalDateTime.now()) && errorCode.equals(ErrorCode.TIME_IN)) {
            throw new TimeInException("Password reset code wait");
        }
        if (passwordReset.getTime().isBefore(LocalDateTime.now()) && errorCode.equals(ErrorCode.TIME_OUT)) {
            throw new TimeOutException("Password reset code drop");
        }
    }

    private void updateCode(PasswordReset passwordReset) {
        passwordReset.setCode(makeResetCode());
        passwordResetRepository.save(passwordReset);
    }

    private void wipeCode(PasswordReset passwordReset) {
        passwordReset.setCode("");
        passwordResetRepository.save(passwordReset);
    }

    private String makeResetCode(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
