package com.memastick.backmem.invite.service;

import com.memastick.backmem.invite.api.InviteAPI;
import com.memastick.backmem.invite.entity.Invite;
import com.memastick.backmem.invite.repository.InviteRepository;
import com.memastick.backmem.sender.component.EmailSender;
import com.memastick.backmem.sender.dto.EmailStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class InviteService {

    private final static Logger LOGGER = LoggerFactory.getLogger(InviteService.class);

    private final static int CODE_SIZE = 8;

    private final InviteRepository inviteRepository;
    private final InviteSendService inviteSendService;

    @Autowired
    public InviteService(
        InviteRepository inviteRepository,
        InviteSendService inviteSendService
    ) {
        this.inviteRepository = inviteRepository;
        this.inviteSendService = inviteSendService;
    }

    public void register(InviteAPI request) {
        Optional<Invite> byEmail = inviteRepository.findByEmail(request.getEmail());

        if (byEmail.isPresent()) {
            inviteSendService.send(byEmail.get());
        } else {
            Invite invite = generateInvite(request);
            EmailStatus send = inviteSendService.send(invite);
            if (send.isError()) inviteRepository.delete(invite);
        }
    }

    private Invite generateInvite(InviteAPI request) {
        Invite invite = new Invite();

        LocalDateTime create = LocalDateTime.now();
        String code = UUID.randomUUID().toString().substring(0, CODE_SIZE);

        invite.setEmail(request.getEmail());
        invite.setNick(request.getNick());
        invite.setCode(code);
        invite.setDate(create);

        inviteRepository.save(invite);

        LOGGER.info("Generate NEW invite - " + invite.toString());

        return invite;
    }

    public List<Invite> readAll() {
        return inviteRepository.findAll();
    }

    public Optional<Invite> findByCode(String code) {
        return inviteRepository.findByCode(code);
    }

}
