package com.memastick.backmem.notification.dto;

import com.memastick.backmem.notification.constant.NotifyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotifyDTO {

    private NotifyType type;

    private String title;
    private String text;
    private String data;
    private String event;

    private boolean isBell = false;

    public NotifyDTO(NotifyType type, String title, String text, String data, String event) {
        this.type = type;
        this.title = title;
        this.text = text;
        this.data = data;
        this.event = event;

        this.isBell = type.isBell();
    }
}
