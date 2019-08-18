package com.memastick.backmem.memotype.controller;

import com.memastick.backmem.memotype.api.MemotypeAPI;
import com.memastick.backmem.memotype.api.MemotypeSetAPI;
import com.memastick.backmem.memotype.service.MemotypeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("memotype")
@AllArgsConstructor
public class MemotypeController {

    private final MemotypeService memotypeService;

    @PutMapping("/create")
    public void create(@RequestBody MemotypeAPI request) {
        memotypeService.create(request);
    }

    //TODO UPDATE

    @GetMapping("/all")
    public List<MemotypeSetAPI> all() {
        return memotypeService.all();
    }
}
