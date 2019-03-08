package com.memastick.backmem.main.controller;

import com.memastick.backmem.main.service.MigrateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MigrateController {

    private final MigrateService migrateService;

    @Autowired
    public MigrateController(
        MigrateService migrateService
    ) {
        this.migrateService = migrateService;
    }

    @PostMapping("migrate")
    public ResponseEntity migrateChromosomes() {
        migrateService.migrate();
        return ResponseEntity.ok().build();
    }
}
