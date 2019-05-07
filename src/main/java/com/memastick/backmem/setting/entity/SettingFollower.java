package com.memastick.backmem.setting.entity;

import com.memastick.backmem.base.entity.AbstractEntity;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "setting_followers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SettingFollower extends AbstractEntity {

    @ManyToOne
    @JoinColumn(nullable = false)
    private Memetick memetick;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User follower;
}