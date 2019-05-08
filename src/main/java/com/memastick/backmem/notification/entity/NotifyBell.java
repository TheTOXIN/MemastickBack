package com.memastick.backmem.notification.entity;

import com.memastick.backmem.base.entity.AbstractEntity;
import com.memastick.backmem.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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
