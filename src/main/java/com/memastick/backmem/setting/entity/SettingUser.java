package com.memastick.backmem.setting.entity;

import com.memastick.backmem.base.AbstractEntity;
import com.memastick.backmem.main.constant.TimeConstant;
import com.memastick.backmem.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "setting_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SettingUser extends AbstractEntity {

    @NaturalId
    @Column(nullable = false, unique = true)
    private UUID userId;

    @Column(nullable = false)
    private ZonedDateTime nickChanged = TimeConstant.START_TIME;

    @Column(nullable = false)
    private boolean pushWork = false;

    public SettingUser(User user) {
        this.userId = user.getId();
    }
}
