package com.memastick.backmem.memetick.repository;

import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.entity.MemetickInventory;
import com.memastick.backmem.memetick.view.MemetickInventoryView;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MemetickInventoryRepository extends CrudRepository<MemetickInventory, UUID> {

    MemetickInventory findByMemetick(Memetick memetick);

    List<MemetickInventory> findByAllowanceFalse();

    List<MemetickInventory> findByCellNotifyFalse();

    @Query(
        "SELECT new com.memastick.backmem.memetick.view.MemetickInventoryView(mi.allowance, mi.cellCreating) " +
            "FROM MemetickInventory mi WHERE mi.memetick = :memetick"
    )
    MemetickInventoryView findInventoryView(@Param("memetick") Memetick memetick);
}
