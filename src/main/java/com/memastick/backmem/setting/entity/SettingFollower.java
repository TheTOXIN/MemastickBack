package com.memastick.backmem.setting.entity;

import com.memastick.backmem.base.AbstractEntity;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.user.entity.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "setting_followers")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraph(name = "joinedSettingMemetick", attributeNodes = {@NamedAttributeNode("memetick")})
@NamedEntityGraph(name = "joinedSettingFollower", attributeNodes = {@NamedAttributeNode("follower")})
public class SettingFollower extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @ToString.Exclude
    private Memetick memetick;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @ToString.Exclude
    private User follower;
}
