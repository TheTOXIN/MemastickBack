package com.memastick.backmem.invite.service;

import com.memastick.backmem.invite.component.EmailHtmlSender;
import com.memastick.backmem.invite.dto.EmailStatus;
import com.memastick.backmem.invite.entity.Invite;
import com.memastick.backmem.invite.repository.InviteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;


@Service
public class InviteSendService {

    private final static String PATH_TEMPLATE = "invite";
    private final static String SUBJECT_TITLE = "MEMASTICK INVITE CODE";

    private final EmailHtmlSender emailHtmlSender;
    private final InviteRepository inviteRepository;

    @Autowired
    public InviteSendService(
        EmailHtmlSender emailHtmlSender,
        InviteRepository inviteRepository
    ) {
        this.emailHtmlSender = emailHtmlSender;
        this.inviteRepository = inviteRepository;
    }

    public void send(Invite invite) {
        Context context = makeContext(invite);

        EmailStatus status = emailHtmlSender.send(
            invite.getEmail(),
            SUBJECT_TITLE,
            PATH_TEMPLATE,
            context
        );

        if (status.isError()) {
            inviteRepository.delete(invite);
        }
    }

    private Context makeContext(Invite invite) {
        Context context = new Context();

        context.setVariable("code", invite.getCode());

        return context;
    }

}
