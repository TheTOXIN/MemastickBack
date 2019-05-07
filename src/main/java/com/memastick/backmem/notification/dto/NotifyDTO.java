package com.memastick.backmem.notification.dto;

import com.memastick.backmem.notification.constant.NotifyType;
import com.memastick.backmem.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotifyDTO {

    @JsonIgnore // TODO remove
    private List<User> users = new ArrayList<>();

    private NotifyType type;

    private String title;
    private String text;
    private String data;
    private String event;

    public NotifyDTO(User user, NotifyType type, String title, String text, String data, String event) {
        this.users = Collections.singletonList(user);
        this.type = type;
        this.title = title;
        this.text = text;
        this.data = data;
        this.event = event;
    }
}
