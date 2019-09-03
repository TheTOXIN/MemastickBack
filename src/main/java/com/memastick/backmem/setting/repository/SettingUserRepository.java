package com.memastick.backmem.setting.repository;

import com.memastick.backmem.setting.entity.SettingUser;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SettingUserRepository extends CrudRepository<SettingUser, UUID> {

    SettingUser findByUserId(UUID userId);

    @Cacheable(value = "pushWork", key = "#userId")
    @Query("SELECT su.pushWork FROM SettingUser su WHERE su.userId = :userId")
    boolean findPushWorkByUserId(@Param("userId") UUID userId);
}
