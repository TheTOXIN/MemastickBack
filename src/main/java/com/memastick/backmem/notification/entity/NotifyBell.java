package com.memastick.backmem.notification.entity;

import com.memastick.backmem.base.AbstractEntity;
import com.memastick.backmem.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notify_bell")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NotifyBell extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @ToString.Exclude
    private User user;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private String link;

    @Column(nullable = false)
    private LocalDateTime creating = LocalDateTime.now();

    @Column(nullable = false)
    private boolean isRead = false;

    public NotifyBell(User user, String text, String link) {
        this.user = user;
        this.text = text;
        this.link = link;
    }
}
