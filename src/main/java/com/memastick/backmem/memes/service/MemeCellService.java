package com.memastick.backmem.memes.service;

import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.entity.MemetickInventory;
import com.memastick.backmem.memetick.repository.MemetickInventoryRepository;
import com.memastick.backmem.memetick.view.CellInventoryView;
import com.memastick.backmem.memetick.view.MemetickInventoryView;
import com.memastick.backmem.security.component.OauthData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static com.memastick.backmem.main.constant.DnaCount.MAX_CREATE;
import static com.memastick.backmem.main.constant.DnaCount.MIN_CREATE;
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

    public int stateCell() {
        return this.stateCell(
            oauthData.getCurrentMemetick()
        );
    }

    public int stateCell(Memetick memetick) {
        CellInventoryView inventoryView = inventoryRepository.findCellInventoryView(memetick);
        return stateCell(inventoryView.getCellCreating());
    }

    public int stateCell(CellInventoryView inventory) {
        return this.stateCell(
            inventory.getCellCreating()
        );
    }

    public int stateCell(LocalDateTime cellCreating) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime end = cellCreating.plusHours(CELL_GROWTH);

        if (end.isBefore(now)) return CELL_SIZE;

        long full = ChronoUnit.MINUTES.between(cellCreating, end);
        long left = ChronoUnit.MINUTES.between(now, end);

        return CELL_SIZE - Math.min((int) (100f / full * left), CELL_SIZE);
    }

    public void updateCell(MemetickInventory inventory) {
        LocalDateTime now = LocalDateTime.now();

        inventory.setCellCombo(nextCombo(now, inventory));
        inventory.setCellCreating(now);
        inventory.setCellNotify(false);

        inventoryRepository.save(inventory);
    }

    public int nextCombo(LocalDateTime now, MemetickInventory inventory) {
        if (validCombo(now, inventory.getCellCreating())) {
            return Math.min(inventory.getCellCombo() + 1, MAX_CREATE);
        } else {
            return MIN_CREATE;
        }
    }

    public int currentCombo(CellInventoryView inventory) {
        if (validCombo(LocalDateTime.now(), inventory.getCellCreating())) {
            return inventory.getCellCombo();
        } else {
            return MIN_CREATE;
        }
    }

    private boolean validCombo(LocalDateTime now, LocalDateTime creating)  {
        return ChronoUnit.HOURS.between(creating, now) < CELL_GROWTH * 2;
    }
}
