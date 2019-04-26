package com.memastick.backmem.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PushNotificationDTO {

    private String title;
    private String body;
    private String action;
    private String icon = "https://www.memastick.ru/assets/images/title-logo.png";

    public PushNotificationDTO(String title, String body, String action) {
        this.title = title;
        this.body = body;
        this.action = action;
    }
}
