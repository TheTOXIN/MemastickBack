package com.memastick.backmem.notification.entity;

import com.memastick.backmem.base.entity.AbstractEntity;
import com.memastick.backmem.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "push_token")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PushToken extends AbstractEntity {

    @OneToOne
    @JoinColumn(nullable = false, unique = true)
    private User user;

    @Column(nullable = false)
    private String token;

    public PushToken(User user) {
        this.user = user;
    }
}
