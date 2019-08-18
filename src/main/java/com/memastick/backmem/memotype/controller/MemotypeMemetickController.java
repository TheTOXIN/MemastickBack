package com.memastick.backmem.memotype.controller;

import com.memastick.backmem.memotype.api.MemotypeMemetickAPI;
import com.memastick.backmem.memotype.service.MemotypeMemetickService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("memotype-memetick")
@AllArgsConstructor
public class MemotypeMemetickController {

    private final MemotypeMemetickService memotypeMemetickService;

    @GetMapping("/read/{memetickId}")
    public MemotypeMemetickAPI read(@PathVariable("memetickId") UUID memetickId) {
        return memotypeMemetickService.read(memetickId);
    }

    @PutMapping("/buy/{memotypeId}")
    public void buy(@PathVariable("memotypeId") UUID memotypeId) {
        memotypeMemetickService.buy(memotypeId);
    }
}
