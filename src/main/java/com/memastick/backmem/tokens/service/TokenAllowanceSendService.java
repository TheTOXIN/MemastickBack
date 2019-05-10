package com.memastick.backmem.tokens.service;

import com.memastick.backmem.memetick.entity.MemetickInventory;
import com.memastick.backmem.memetick.repository.MemetickInventoryRepository;
import com.memastick.backmem.notification.constant.NotifyType;
import com.memastick.backmem.notification.service.NotifyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TokenAllowanceSendService {

    private static final Logger log = LoggerFactory.getLogger(TokenAllowanceSendService.class);

    private final MemetickInventoryRepository inventoryRepository;
    private final NotifyService notificationService;

    @Autowired
    public TokenAllowanceSendService(
        MemetickInventoryRepository inventoryRepository,
        NotifyService notificationService
    ) {
        this.inventoryRepository = inventoryRepository;
        this.notificationService = notificationService;
    }

    @Scheduled(cron = "0 0 12 * * *", zone = "UTC")
    public void allowance() {
        log.info("START ALLOWANCE");

        List<MemetickInventory> inventories = inventoryRepository.findByAllowanceFalse();
        inventories.forEach(inventory -> inventory.setAllowance(true));
        inventoryRepository.saveAll(inventories);
        notificationService.sendALLOWANCE();

        log.info("END ALLOWANCE");
    }
}
