package com.memastick.backmem.memotype.controller;

import com.memastick.backmem.memotype.api.MemotypeAPI;
import com.memastick.backmem.memotype.api.MemotypeMemetickAPI;
import com.memastick.backmem.memotype.service.MemotypeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("memotype")
@AllArgsConstructor
public class MemotypeController {

    private final MemotypeService memotypeService;

    @GetMapping("/all")
    public MemotypeMemetickAPI all() {
        return memotypeService.all();
    }

    @GetMapping("/read/{id}")
    public MemotypeAPI read(@PathVariable("id") UUID memotypeId) {
        return memotypeService.read(memotypeId);
    }

    @PutMapping("/create")
    public void create(@RequestBody MemotypeAPI request) {
        memotypeService.create(request);
    }
}
