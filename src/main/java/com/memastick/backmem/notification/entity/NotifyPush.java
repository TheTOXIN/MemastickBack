package com.memastick.backmem.notification.entity;

import com.memastick.backmem.base.AbstractEntity;
import com.memastick.backmem.user.entity.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "notify_push")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NotifyPush extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @ToString.Exclude
    private User user;

    @Column(nullable = false, unique = true)
    private String token;

    public NotifyPush(String token) {
        this.token = token;
    }
}
