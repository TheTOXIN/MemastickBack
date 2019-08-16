package com.memastick.backmem.translator.controller;

import com.memastick.backmem.main.api.IdAPI;
import com.memastick.backmem.translator.serivce.TranslatorAdminService;
import com.memastick.backmem.translator.serivce.TranslatorUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("translator")
@AllArgsConstructor
public class TranslatorController {

    private final TranslatorUserService translatorUserService;
    private final TranslatorAdminService translatorAdminService;

    @PostMapping("/user")
    public void userPublish(@RequestBody IdAPI request) {
        translatorUserService.userPublish(request.getId());
    }

    @PostMapping("/admin")
    public void adminPublish(@RequestBody IdAPI request) {
        translatorAdminService.adminPublish(request.getId());
    }
}
