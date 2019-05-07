package com.memastick.backmem.notification.controller;

import com.memastick.backmem.notification.api.NotifyBellAPI;
import com.memastick.backmem.notification.impl.NotifyBellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/notify/bell")
public class NotifyBellController {

    private final NotifyBellService bellService;

    @Autowired
    public NotifyBellController(NotifyBellService bellService) {
        this.bellService = bellService;
    }

    @GetMapping("/read")
    public List<NotifyBellAPI> read() {
        return bellService.read();
    }

    @DeleteMapping("/clear")
    public void clear() {
        bellService.clear();
    }

    @DeleteMapping("/clear/{id}")
    public void clear(@PathVariable("id")UUID id) {
        bellService.clear(id);
    }
}
