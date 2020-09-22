package com.memastick.backmem.sender.service;

import com.memastick.backmem.main.constant.LinkConstant;
import com.memastick.backmem.security.entity.InviteCode;
import com.memastick.backmem.sender.component.EmailHtmlSender;
import com.memastick.backmem.sender.dto.EmailStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;


@Service
@RequiredArgsConstructor
public class SenderInviteCodeService {

    private final static String PATH_TEMPLATE = "invite-code";
    private final static String SUBJECT_TITLE = "ИНВАЙТ КОД";

    private final EmailHtmlSender emailHtmlSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public EmailStatus send(InviteCode inviteCode) {
        Context context = makeContext(inviteCode);

        return emailHtmlSender.send(
            fromEmail,
            inviteCode.getEmail(),
            SUBJECT_TITLE,
            PATH_TEMPLATE,
            context
        );
    }

    private Context makeContext(InviteCode inviteCode) {
        Context context = new Context();

        context.setVariable("code", inviteCode.getCode());
        context.setVariable("link", LinkConstant.LINK_REG_INVITE + inviteCode.getCode());

        return context;
    }

}
