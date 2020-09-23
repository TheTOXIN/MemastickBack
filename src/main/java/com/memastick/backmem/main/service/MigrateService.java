package com.memastick.backmem.main.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MigrateService {

    @Transactional
    public void migrate() {

    }
}
