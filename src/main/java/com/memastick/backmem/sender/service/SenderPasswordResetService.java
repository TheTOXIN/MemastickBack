package com.memastick.backmem.sender.service;

import com.memastick.backmem.main.constant.LinkConstant;
import com.memastick.backmem.security.entity.PasswordReset;
import com.memastick.backmem.sender.component.EmailHtmlSender;
import com.memastick.backmem.sender.dto.EmailStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;


@Service
@RequiredArgsConstructor
public class SenderPasswordResetService {

    private final static String PATH_TEMPLATE = "password-reset";
    private final static String SUBJECT_TITLE = "ВОССТАНОВЛЕНИЕ ПАРОЛЯ";

    private final EmailHtmlSender emailHtmlSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public EmailStatus send(PasswordReset passwordReset, String email) {
        Context context = makeContext(passwordReset);

        return emailHtmlSender.send(
            fromEmail,
            email,
            SUBJECT_TITLE,
            PATH_TEMPLATE,
            context
        );
    }

    private Context makeContext(PasswordReset passwordReset) {
        Context context = new Context();

        context.setVariable("code", passwordReset.getCode());
        context.setVariable("login", passwordReset.getLogin());
        context.setVariable("link", LinkConstant.LINK_FORGET_PASSWORD + passwordReset.getCode());

        return context;
    }
}
