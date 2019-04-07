package com.memastick.backmem.notification.dto;

import com.memastick.backmem.notification.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {

    private NotificationType type;
    private String data;

}
