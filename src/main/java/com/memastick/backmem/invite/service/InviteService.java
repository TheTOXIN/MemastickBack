package com.memastick.backmem.invite.service;

import com.memastick.backmem.invite.api.InviteAPI;
import com.memastick.backmem.invite.entity.Invite;
import com.memastick.backmem.invite.repository.InviteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.UUID;


@Service
public class InviteService {

    private final InviteRepository inviteRepository;

    @Autowired
    public InviteService(InviteRepository inviteRepository) {
        this.inviteRepository = inviteRepository;
    }

    public void register(InviteAPI request) {
        Invite invite = new Invite();

        ZonedDateTime create = ZonedDateTime.now(request.getZone());
        String code = UUID.randomUUID().toString().substring(0, 9);

        invite.setEmail(request.getEmail());
        invite.setCode(code);
        invite.setCreate(create);

        inviteRepository.save(invite);
    }

}
