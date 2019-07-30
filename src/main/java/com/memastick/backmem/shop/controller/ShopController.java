package com.memastick.backmem.shop.controller;

import com.memastick.backmem.shop.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("shop")
public class ShopController {

    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @PostMapping("test/{count}")
    public void test(@PathVariable("count") int count) {
        shopService.test(count);
    }
}
