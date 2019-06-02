package com.memastick.backmem.memes.service;

import com.memastick.backmem.memetick.entity.MemetickInventory;
import com.memastick.backmem.memetick.repository.MemetickInventoryRepository;
import com.memastick.backmem.notification.service.NotifyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemeCellCheckerService {

//    private final static Logger LOG = LoggerFactory.getLogger(MemeCellCheckerService.class);
//
//    private final MemetickInventoryRepository inventoryRepository;
//    private final MemetickInventoryService inventoryService;
//    private final NotifyService notifyService;
//
//    public MemeCellCheckerService(
//        MemetickInventoryRepository inventoryRepository,
//        MemetickInventoryService inventoryService,
//        NotifyService notifyService
//    ) {
//        this.inventoryRepository = inventoryRepository;
//        this.inventoryService = inventoryService;
//        this.notifyService = notifyService;
//    }
//
//    @Scheduled(cron = "0 0 */1 * * *", zone = "UTC")
//    public void notification() {
//        LOG.info("START check cell notify");
//
//        List<MemetickInventory> inventories = inventoryRepository.findByCellNotifyFalse();
//
//        inventories
//            .stream()
//            .filter(inventoryService::checkState)
//            .forEach(inventory -> notifyService.sendCELL(inventory.getMemetick()));
//
//        inventories.forEach(i -> i.setCellNotify(true));
//
//        inventoryRepository.saveAll(inventories);
//    }
}
