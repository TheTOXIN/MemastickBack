package com.memastick.backmem.memecoin.controller;

import com.memastick.backmem.memecoin.service.PickaxeService;
import com.memastick.backmem.memecoin.api.PickaxeAPI;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pickaxe")
@AllArgsConstructor
public class PickaxeController {

    private final PickaxeService pickaxeService;

    @GetMapping
    public PickaxeAPI pickaxe() {
        return pickaxeService.generate();
    }
}
