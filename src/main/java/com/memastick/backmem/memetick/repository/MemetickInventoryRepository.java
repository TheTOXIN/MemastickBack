package com.memastick.backmem.memetick.repository;

import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.entity.MemetickInventory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MemetickInventoryRepository extends CrudRepository<MemetickInventory, UUID> {

    MemetickInventory findByMemetick(Memetick memetick);

    List<MemetickInventory> findByAllowanceFalse();

}
