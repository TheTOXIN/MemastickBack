package com.memastick.backmem.setting.repository;

import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.setting.entity.SettingFollower;
import com.memastick.backmem.user.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SettingFollowerRepository extends CrudRepository<SettingFollower, UUID> {

    @EntityGraph(value = "joinedSettingFollower")
    List<SettingFollower> findAllByMemetick(Memetick memetick);

    @EntityGraph(value = "joinedSettingMemetick")
    List<SettingFollower> findAllByFollower(User follower);

    Optional<SettingFollower> findByMemetickAndFollower(Memetick memetick, User follower);
}
