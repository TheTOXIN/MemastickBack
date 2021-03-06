package com.memastick.backmem.memetick.repository;

import com.memastick.backmem.memetick.entity.MemetickAvatar;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MemetickAvatarRepository extends CrudRepository<MemetickAvatar, UUID> {

    MemetickAvatar findByMemetickId(UUID memetickId);

}
