package com.memastick.backmem.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotifyPushDTO {

    private String title;
    private String body;
    private String icon = "https://www.memastick.ru/assets/images/title-logo.png";

    public NotifyPushDTO(String title, String body) {
        this.title = title;
        this.body = body;
    }
}
