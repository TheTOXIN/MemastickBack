package com.memastick.backmem.memetick.repository;

import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.entity.MemetickInventory;
import com.memastick.backmem.memetick.view.CellInventoryView;
import com.memastick.backmem.memetick.view.MemetickInventoryView;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface MemetickInventoryRepository extends CrudRepository<MemetickInventory, UUID> {

    @EntityGraph(value = "joinedInventoryMemetick")
    MemetickInventory findByMemetick(Memetick memetick);

    @EntityGraph(value = "joinedInventoryMemetick")
    List<MemetickInventory> findByAllowanceFalse();

    @EntityGraph(value = "joinedInventoryMemetick")
    List<MemetickInventory> findByCellNotifyFalse();

    @Query(
        "SELECT new com.memastick.backmem.memetick.view.MemetickInventoryView(mi.allowance, mi.cellCreating, mi.pickaxeCreating) " +
            "FROM MemetickInventory mi WHERE mi.memetick = :memetick"
    )
    MemetickInventoryView findInventoryView(@Param("memetick") Memetick memetick);

    @Query(
        "SELECT new com.memastick.backmem.memetick.view.CellInventoryView(mi.cellCombo, mi.cellCreating) " +
            "FROM MemetickInventory mi WHERE mi.memetick = :memetick"
    )
    CellInventoryView findCellInventoryView(@Param("memetick") Memetick memetick);

    @Modifying
    @Query("UPDATE MemetickInventory mi SET mi.pickaxeCreating = :creating WHERE mi.memetick.id = :memetickId")
    void updatePickaxe(
        @Param("creating") LocalDateTime creating,
        @Param("memetickId") UUID memetickId
    );
}
