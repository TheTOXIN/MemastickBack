package com.memastick.backmem.memetick.controller;

import com.memastick.backmem.memetick.api.CellAPI;
import com.memastick.backmem.memetick.api.MemetickInventoryAPI;
import com.memastick.backmem.memetick.service.MemetickInventoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("memetick-inventories")
public class MemetickInventoryController {

    private final MemetickInventoryService inventoryService;

    public MemetickInventoryController(MemetickInventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("cell")
    public CellAPI stateCell() {
        return inventoryService.readStateCell();
    }

    @GetMapping("all")
    public MemetickInventoryAPI readAll() {
        return inventoryService.readAll();
    }
}
