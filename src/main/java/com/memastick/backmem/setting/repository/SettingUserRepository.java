package com.memastick.backmem.setting.repository;

import com.memastick.backmem.setting.entity.SettingUser;
import com.memastick.backmem.user.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SettingUserRepository extends CrudRepository<SettingUser, UUID> {

    SettingUser findByUser(User user);
}
