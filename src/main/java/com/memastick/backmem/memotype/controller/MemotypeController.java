package com.memastick.backmem.memotype.controller;

import com.memastick.backmem.memotype.api.MemotypeAPI;
import com.memastick.backmem.memotype.api.MemotypeMemetickAPI;
import com.memastick.backmem.memotype.service.MemotypeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("memotype")
@AllArgsConstructor
public class MemotypeController {

    private final MemotypeService memotypeService;


    @GetMapping("/collection")
    public MemotypeMemetickAPI collection() {
        return memotypeService.all();
    }

    @PutMapping("/create")
    public void create(@RequestBody MemotypeAPI request) {
        memotypeService.create(request);
    }

    //TODO UPDATE
}
