package com.memastick.backmem.security.service;

import com.memastick.backmem.errors.consts.ErrorCode;
import com.memastick.backmem.errors.exception.EntityNotFoundException;
import com.memastick.backmem.errors.exception.TimeInException;
import com.memastick.backmem.errors.exception.TimeOutException;
import com.memastick.backmem.main.util.ValidationUtil;
import com.memastick.backmem.security.api.PasswordResetTakeAPI;
import com.memastick.backmem.security.constant.SecurityStatus;
import com.memastick.backmem.security.entity.PasswordReset;
import com.memastick.backmem.security.repository.PasswordResetRepository;
import com.memastick.backmem.sender.dto.EmailStatus;
import com.memastick.backmem.sender.service.SenderPasswordResetService;
import com.memastick.backmem.user.entity.User;
import com.memastick.backmem.user.repository.UserRepository;
import com.memastick.backmem.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final SenderPasswordResetService senderPasswordResetService;
    private final PasswordResetRepository passwordResetRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Transactional
    public EmailStatus send(String email) {
        User user = userRepository
            .findByEmail(email)
            .orElseThrow(() -> new EntityNotFoundException(User.class, "email"));

        Optional<PasswordReset> optionalReset = passwordResetRepository.findByLogin(user.getLogin());
        PasswordReset passwordReset = optionalReset.orElse(generateReset(user));

        if (optionalReset.isPresent()) {
            checkTimeReset(passwordReset, ErrorCode.TIME_IN);
            updateCode(passwordReset);
        }

        return senderPasswordResetService.send(passwordReset, email);
    }

    @Transactional
    public SecurityStatus take(PasswordResetTakeAPI request) {
        Optional<PasswordReset> byCode = passwordResetRepository.findByCode(request.getCode());
        if (byCode.isEmpty()) return SecurityStatus.INVITE;

        PasswordReset passwordReset = byCode.get();
        checkTimeReset(passwordReset, ErrorCode.TIME_OUT);

        if (!ValidationUtil.checkPassword(request.getPassword())) return SecurityStatus.PASSWORD_INVALID;
        if (!request.getPassword().equals(request.getPasswordRepeat())) return SecurityStatus.PASSWORD_REPEAT;

        userService.updatePassword(passwordReset.getLogin(), request.getPassword());
        wipeCode(passwordReset);

        return SecurityStatus.SUCCESSFUL;
    }

    private PasswordReset generateReset(User user) {
        PasswordReset passwordReset = new PasswordReset();

        passwordReset.setLogin(user.getLogin());
        passwordReset.setCode(makeResetCode());
        passwordReset.setTime(makeTimeCode());

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
        passwordReset.setTime(makeTimeCode());
        passwordResetRepository.save(passwordReset);
    }

    private void wipeCode(PasswordReset passwordReset) {
        passwordReset.setCode("");
        passwordResetRepository.save(passwordReset);
    }

    private String makeResetCode() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
    }

    private LocalDateTime makeTimeCode() {
        return LocalDateTime.now().plusDays(1);
    }
}
