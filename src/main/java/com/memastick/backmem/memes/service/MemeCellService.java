package com.memastick.backmem.memes.service;

import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.entity.MemetickInventory;
import com.memastick.backmem.memetick.repository.MemetickInventoryRepository;
import com.memastick.backmem.memetick.view.MemetickInventoryView;
import com.memastick.backmem.security.component.OauthData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static com.memastick.backmem.main.constant.GlobalConstant.CELL_GROWTH;
import static com.memastick.backmem.main.constant.GlobalConstant.CELL_SIZE;

@Service
@AllArgsConstructor
public class MemeCellService {

    private final MemetickInventoryRepository inventoryRepository;
    private final OauthData oauthData;

    public boolean checkState() {
        return this.stateCell() == CELL_SIZE;
    }

    public boolean checkState(MemetickInventoryView inventoryView) {
        return this.stateCell(inventoryView.getCellCreating()) == CELL_SIZE;
    }

    public boolean checkState(MemetickInventory inventory) {
        return this.stateCell(inventory.getCellCreating()) == CELL_SIZE;
    }

    public void updateCell(Memetick memetick) {
        MemetickInventory inventory = inventoryRepository.findByMemetick(memetick);

        inventory.setCellCreating(LocalDateTime.now());
        inventory.setCellNotify(false);

        inventoryRepository.save(inventory);
    }

    public int stateCell() {
        Memetick memetick = oauthData.getCurrentMemetick();
        MemetickInventory inventory = inventoryRepository.findByMemetick(memetick);

        return stateCell(inventory.getCellCreating());
    }

    public int stateCell(LocalDateTime cellCreating) {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime end = cellCreating.plusHours(CELL_GROWTH);

        if (end.isBefore(now)) return CELL_SIZE;

        long full = ChronoUnit.MINUTES.between(cellCreating, end);
        long lift = ChronoUnit.MINUTES.between(now, end);

        return CELL_SIZE - Math.min((int) (100f / full * lift), CELL_SIZE);
    }
}
