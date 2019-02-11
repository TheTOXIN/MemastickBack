package com.memastick.backmem.main.controller;

import com.memastick.backmem.main.service.MigrateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("migrate")
public class MigrateController {

    private final MigrateService migrateService;

    @Autowired
    public MigrateController(
        MigrateService migrateService
    ) {
        this.migrateService = migrateService;
    }

    @PostMapping("chromosomes")
    public ResponseEntity migrateChromosomes() {
        migrateService.migrate();
        return ResponseEntity.ok().build();
    }
}
