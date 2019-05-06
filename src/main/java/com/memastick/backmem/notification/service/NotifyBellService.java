package com.memastick.backmem.notification.service;

import com.memastick.backmem.notification.dto.NotifyDTO;
import com.memastick.backmem.notification.interfaces.NotifySender;
import org.springframework.stereotype.Service;

@Service
public class NotifyBellService implements NotifySender {

    @Override
    public void send(NotifyDTO dto) {

    }
}
