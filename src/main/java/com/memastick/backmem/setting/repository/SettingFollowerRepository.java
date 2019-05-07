package com.memastick.backmem.setting.repository;

import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.setting.entity.SettingFollower;
import com.memastick.backmem.user.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SettingFollowerRepository extends CrudRepository<SettingFollower, UUID> {

    Optional<SettingFollower> findByMemetickAndFollower(Memetick memetick, User follower);
}
