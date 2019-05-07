package com.memastick.backmem.notification.api;

import com.memastick.backmem.notification.constant.NotifyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotifyBellAPI {

    private NotifyType type;
    private String text;
    private String link;
    private boolean isRead;

}
