package com.memastick.backmem.setting.entity;

import com.memastick.backmem.base.entity.AbstractEntity;
import com.memastick.backmem.main.constant.TimeConstant;
import com.memastick.backmem.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "setting_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SettingUser extends AbstractEntity {

    @OneToOne
    @JoinColumn(nullable = false, unique = true)
    private User user;

    @Column(nullable = false)
    private ZonedDateTime nickChanged = TimeConstant.START_TIME;

    @Column
    private boolean pushWork = false;

    public SettingUser(User user) {
        this.user = user;
    }
}
