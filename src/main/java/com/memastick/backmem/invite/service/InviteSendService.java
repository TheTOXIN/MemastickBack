package com.memastick.backmem.invite.service;

import com.memastick.backmem.sender.component.EmailHtmlSender;
import com.memastick.backmem.sender.dto.EmailStatus;
import com.memastick.backmem.invite.entity.Invite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;


@Service
public class InviteSendService {

    private final static String PATH_TEMPLATE = "invite";
    private final static String SUBJECT_TITLE = "MEMASTICK INVITE CODE";

    private final EmailHtmlSender emailHtmlSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    public InviteSendService(
        EmailHtmlSender emailHtmlSender
    ) {
        this.emailHtmlSender = emailHtmlSender;
    }

    public EmailStatus send(Invite invite) {
        Context context = makeContext(invite);

        return emailHtmlSender.send(
            fromEmail,
            invite.getEmail(),
            SUBJECT_TITLE,
            PATH_TEMPLATE,
            context
        );
    }

    private Context makeContext(Invite invite) {
        Context context = new Context();

        context.setVariable("code", invite.getCode());
        context.setVariable("nick", invite.getNick());

        return context;
    }

}
