package com.memastick.backmem.notification.api;

import com.memastick.backmem.notification.constant.NotifyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotifyBellAPI {

    private UUID id;
    private NotifyType type;
    private String text;
    private String link;
    private boolean isRead;

}
