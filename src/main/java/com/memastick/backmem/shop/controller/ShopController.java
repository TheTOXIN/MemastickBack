package com.memastick.backmem.shop.controller;

import com.memastick.backmem.shop.service.ShopService;
import com.memastick.backmem.tokens.constant.TokenType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("shop")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    @PostMapping("cookies/{count}")
    public void cookies(@PathVariable("count") int count) {
        shopService.cookies(count);
    }

    @PostMapping("tokens/{type}/{count}")
    public void tokens(
        @PathVariable("type") TokenType type,
        @PathVariable("count") int count
    ) {
        shopService.tokens(type, count);
    }
}
