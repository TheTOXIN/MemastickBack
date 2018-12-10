package com.memastick.backmem.invite.service;

import com.memastick.backmem.invite.entity.Invite;
import org.springframework.stereotype.Service;

@Service
public class InviteSendService {

    public void send(Invite invite) {
        System.out.printf("%d - SEND %s TO %s%n", invite.getId(), invite.getCode(), invite.getEmail());
    }

}
