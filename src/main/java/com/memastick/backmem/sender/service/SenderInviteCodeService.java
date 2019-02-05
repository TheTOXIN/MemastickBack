package com.memastick.backmem.sender.service;

import com.memastick.backmem.security.entity.InviteCode;
import com.memastick.backmem.sender.component.EmailHtmlSender;
import com.memastick.backmem.sender.dto.EmailStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;


@Service
public class SenderInviteCodeService {

    private final static String PATH_TEMPLATE = "invite-code";
    private final static String SUBJECT_TITLE = "МЕМАСТИК ИНВАЙТ КОД";

    private final EmailHtmlSender emailHtmlSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    public SenderInviteCodeService(
        EmailHtmlSender emailHtmlSender
    ) {
        this.emailHtmlSender = emailHtmlSender;
    }

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
        context.setVariable("nick", inviteCode.getNick());

        return context;
    }

}
