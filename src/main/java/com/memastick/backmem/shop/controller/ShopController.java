package com.memastick.backmem.shop.controller;

import com.memastick.backmem.shop.service.ShopService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("shop")
public class ShopController {

    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @PostMapping("cookies/{count}")
    public void cookies(@PathVariable("count") int count) {
        shopService.cookies(count);
    }
}
