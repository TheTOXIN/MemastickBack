package com.memastick.backmem.invite.service;

import com.memastick.backmem.invite.api.InviteAPI;
import com.memastick.backmem.invite.entity.Invite;
import com.memastick.backmem.invite.repository.InviteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class InviteService {

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
            inviteSendService.send(generateInvite(request));
        }
    }

    private Invite generateInvite(InviteAPI request) {
        Invite invite = new Invite();

        ZonedDateTime create = ZonedDateTime.now(request.getZone());
        String code = UUID.randomUUID().toString().substring(0, CODE_SIZE);

        invite.setEmail(request.getEmail());
        invite.setCode(code);
        invite.setCreate(create);

        inviteRepository.save(invite);

        return invite;
    }

    public List<Invite> readAll() {
        return inviteRepository.findAll();
    }

    public Optional<Invite> findByCode(String code) {
        return inviteRepository.findByCode(code);
    }

}
