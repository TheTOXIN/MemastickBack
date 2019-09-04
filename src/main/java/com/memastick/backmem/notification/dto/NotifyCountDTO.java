package com.memastick.backmem.notification.dto;

import com.memastick.backmem.notification.constant.NotifyCountAction;
import com.memastick.backmem.notification.constant.NotifyCountType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotifyCountDTO {

    private NotifyCountType type;
    private NotifyCountAction action;
}
