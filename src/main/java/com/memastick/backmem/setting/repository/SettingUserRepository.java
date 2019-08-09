package com.memastick.backmem.setting.repository;

import com.memastick.backmem.setting.entity.SettingUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SettingUserRepository extends CrudRepository<SettingUser, UUID> {

    SettingUser findByUserId(UUID userId);
}
