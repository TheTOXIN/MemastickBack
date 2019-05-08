package com.memastick.backmem.notification.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotifyBellAPI {

    private UUID id;
    private String text;
    private String link;
    private boolean isRead;

}
