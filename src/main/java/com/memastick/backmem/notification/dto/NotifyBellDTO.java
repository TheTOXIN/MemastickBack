package com.memastick.backmem.notification.dto;

import com.memastick.backmem.notification.constant.NotifyType;
import com.memastick.backmem.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotifyBellDTO {

    private User user;
    private NotifyType type;
    private String data;

}
