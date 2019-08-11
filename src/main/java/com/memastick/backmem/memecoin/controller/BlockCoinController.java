package com.memastick.backmem.memecoin.controller;

import com.memastick.backmem.memecoin.api.BlockHashAPI;
import com.memastick.backmem.memecoin.api.BlockNonceAPI;
import com.memastick.backmem.memecoin.service.BlockCoinService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("block-coins")
@AllArgsConstructor
public class BlockCoinController {

    private final BlockCoinService blockCoinService;

    @GetMapping("make")
    public BlockHashAPI makeBlock() {
        return new BlockHashAPI(
            blockCoinService.makeBlock()
        );
    }

    @PatchMapping("mine")
    public void mineBlock(@RequestBody BlockNonceAPI api) {
        blockCoinService.mineBlock(
            api.getNonce()
        );
    }

    @PutMapping("flush")
    public void flushBlock() {
        blockCoinService.flushBlock();
    }
}
