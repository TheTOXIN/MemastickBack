package com.memastick.backmem.notification.service;

import com.memastick.backmem.notification.constant.NotifyType;
import com.memastick.backmem.notification.dto.NotifyDTO;
import com.memastick.backmem.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.function.Consumer;

@Service
public class NotifyService {

    private final NotifyPushService pushService;
    private final NotifyBellService bellService;
    private final NotifyWebService webService;
    private final UserRepository userRepository;

    private HashMap<NotifyType, Consumer<NotifyDTO>> typer = new HashMap<>();

    @Autowired
    public NotifyService(
        NotifyPushService pushService,
        NotifyBellService bellService,
        NotifyWebService webService,
        UserRepository userRepository
    ) {
        typer.put(NotifyType.DNA, this::sendDNA);
        typer.put(NotifyType.CELL, this::sendCELL);
        typer.put(NotifyType.MEME, this::sendMEME);
        typer.put(NotifyType.TOKEN, this::sendTOKEN);
        typer.put(NotifyType.CREATING, this::sendCREATING);
        typer.put(NotifyType.ALLOWANCE, this::sendALLOWANCE);

        this.pushService = pushService;
        this.bellService = bellService;
        this.webService = webService;
        this.userRepository = userRepository;
    }

    @Async
    public void send(NotifyType type, NotifyDTO dto) {
        typer.get(type).accept(dto);
    }

    private void sendDNA(NotifyDTO dto) {
        System.out.println(dto);
    }

    private void sendCELL(NotifyDTO dto) {
        System.out.println(dto);
    }

    private void sendMEME(NotifyDTO dto) {
        System.out.println(dto);
    }

    private void sendTOKEN(NotifyDTO dto) {
        System.out.println(dto);
    }

    private void sendCREATING(NotifyDTO dto) {
        System.out.println(dto);
    }

    private void sendALLOWANCE(NotifyDTO dto) {
        System.out.println(dto);
    }
}
