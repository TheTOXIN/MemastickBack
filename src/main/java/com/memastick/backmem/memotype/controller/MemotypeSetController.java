package com.memastick.backmem.memotype.controller;

import com.memastick.backmem.memotype.api.MemotypeAPI;
import com.memastick.backmem.memotype.api.MemotypeSetAPI;
import com.memastick.backmem.memotype.service.MemotypeSetService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("memotype-set")
@AllArgsConstructor
public class MemotypeSetController {

    private final MemotypeSetService memotypeSetService;

    @GetMapping("/collection")
    public List<MemotypeSetAPI> all() {
        return memotypeSetService.collection();
    }

    @PutMapping("/create")
    public void create(@RequestBody MemotypeSetAPI request) {
        memotypeSetService.create(request);
    }

    //TODO UPDATE
}
