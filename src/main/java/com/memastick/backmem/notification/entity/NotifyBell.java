package com.memastick.backmem.notification.entity;

import com.memastick.backmem.base.entity.AbstractEntity;
import com.memastick.backmem.notification.constant.NotifyType;
import com.memastick.backmem.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "notify_bell")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NotifyBell extends AbstractEntity {

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @Column(nullable = false)
    private NotifyType type;

    @Column(nullable = false)
    private boolean isRead = false;

    @Column
    private String data;

    // TODO add creating

    public NotifyBell(User user, NotifyType type, String data) {
        this.user = user;
        this.type = type;
        this.data = data;
    }
}
