package com.memastick.backmem.tokens.service;

import com.memastick.backmem.memetick.entity.MemetickInventory;
import com.memastick.backmem.memetick.repository.MemetickInventoryRepository;
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

    @Autowired
    public TokenAllowanceSendService(
        MemetickInventoryRepository inventoryRepository
    ) {
        this.inventoryRepository = inventoryRepository;
    }

    @Scheduled(cron = "0 0 12 * * *", zone = "UTC")
    public void allowance() {
        log.info("START ALLOWANCE");

        List<MemetickInventory> inventories = inventoryRepository.findByAllowanceFalse();
        inventories.forEach(inventory -> inventory.setAllowance(true));
        inventoryRepository.saveAll(inventories);

        log.info("END ALLOWANCE");
    }
}