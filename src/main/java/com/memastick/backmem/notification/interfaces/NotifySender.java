package com.memastick.backmem.notification.interfaces;

import com.memastick.backmem.notification.dto.NotifyDTO;

public interface NotifySender {

    void send(NotifyDTO dto);

}
