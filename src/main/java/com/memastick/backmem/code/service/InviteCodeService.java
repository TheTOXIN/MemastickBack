package com.memastick.backmem.code.service;

import com.memastick.backmem.code.api.InviteCodeAPI;
import com.memastick.backmem.code.entity.InviteCode;
import com.memastick.backmem.code.repository.InviteCodeRepository;
import com.memastick.backmem.errors.exception.EntityNotFound;
import com.memastick.backmem.sender.dto.EmailStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


@Service
public class InviteCodeService {

    private final static Logger LOGGER = LoggerFactory.getLogger(InviteCodeService.class);

    private final static int CODE_SIZE = 8;

    private final InviteCodeRepository inviteCodeRepository;
    private final InviteCodeSendService inviteCodeSendService;

    @Autowired
    public InviteCodeService(
            InviteCodeRepository inviteCodeRepository,
            InviteCodeSendService inviteCodeSendService
    ) {
        this.inviteCodeRepository = inviteCodeRepository;
        this.inviteCodeSendService = inviteCodeSendService;
    }

    public void register(InviteCodeAPI request) {
        Optional<InviteCode> byEmail = inviteCodeRepository.findByEmail(request.getEmail());
        if (byEmail.isPresent()) return;
        InviteCode inviteCode = generateInvite(request);
        inviteCodeRepository.save(inviteCode);
    }

    public EmailStatus send(String code) {
        InviteCode inviteCode = findByCode(code);
        EmailStatus emailStatus = inviteCodeSendService.send(inviteCode);

        if (emailStatus.isSuccess()) {
            inviteCode.setSend(true);
            inviteCodeRepository.save(inviteCode);
        }

        return emailStatus;
    }

    public void take(String code) {
        InviteCode inviteCode = findByCode(code);
        inviteCode.setTake(true);
        inviteCodeRepository.save(inviteCode);
    }

    private InviteCode generateInvite(InviteCodeAPI request) {
        InviteCode inviteCode = new InviteCode();

        LocalDateTime create = LocalDateTime.now();
        String code = UUID.randomUUID().toString().substring(0, CODE_SIZE);

        inviteCode.setEmail(request.getEmail());
        inviteCode.setNick(request.getNick());
        inviteCode.setCode(code);
        inviteCode.setDate(create);

        LOGGER.info("Generate NEW inviteCode - " + inviteCode.toString());

        return inviteCode;
    }

    public InviteCode findByCode(String code) {
        Optional<InviteCode> byCode = inviteCodeRepository.findByCode(code);
        if (byCode.isEmpty()) throw new EntityNotFound(InviteCode.class);
        return byCode.get();
    }

}
