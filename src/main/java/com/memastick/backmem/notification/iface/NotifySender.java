package com.memastick.backmem.notification.iface;

import com.memastick.backmem.notification.dto.NotifyDTO;
import com.memastick.backmem.user.entity.User;

import java.util.List;

public interface NotifySender {

    void send(List<User> users, NotifyDTO dto);

}
