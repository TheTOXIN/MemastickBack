package com.memastick.backmem.notification.iface;

import com.memastick.backmem.notification.dto.NotifyDTO;

public interface NotifySender {

    void send(NotifyDTO dto);

}
