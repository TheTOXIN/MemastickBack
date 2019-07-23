package com.memastick.backmem.security.service;

import com.memastick.backmem.errors.consts.ErrorCode;
import com.memastick.backmem.errors.exception.ValidationException;
import com.memastick.backmem.main.util.ValidationUtil;
import com.memastick.backmem.sender.service.SenderInviteCodeService;
import com.memastick.backmem.security.api.InviteCodeAPI;
import com.memastick.backmem.security.entity.InviteCode;
import com.memastick.backmem.security.repository.InviteCodeRepository;
import com.memastick.backmem.errors.exception.EntityNotFoundException;
import com.memastick.backmem.sender.dto.EmailStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


@Service
public class InviteCodeService {

    private final static Logger LOGGER = LoggerFactory.getLogger(InviteCodeService.class);

    private final static int CODE_SIZE = 8;

    private final InviteCodeRepository inviteCodeRepository;
    private final SenderInviteCodeService senderInviteCodeService;

    @Value("${memastick.invite.auto}")
    boolean autoInvite;

    @Autowired
    public InviteCodeService(
        InviteCodeRepository inviteCodeRepository,
        SenderInviteCodeService senderInviteCodeService
    ) {
        this.inviteCodeRepository = inviteCodeRepository;
        this.senderInviteCodeService = senderInviteCodeService;
    }

    public void register(InviteCodeAPI request) {
        Optional<InviteCode> optional = inviteCodeRepository.findByEmail(request.getEmail());

        InviteCode invite = optional.orElseGet(() -> generateInvite(request));

        if (autoInvite) send(invite);
    }

    public EmailStatus send(String code) {
        return send(
            findByCode(code)
        );
    }

    public EmailStatus send(InviteCode inviteCode) {
        EmailStatus emailStatus = senderInviteCodeService.send(inviteCode);

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

        if (!ValidationUtil.checkNick(request.getNick())) throw new ValidationException(ErrorCode.INVALID_NICK);

        inviteCode.setEmail(request.getEmail());
        inviteCode.setNick(request.getNick());
        inviteCode.setCode(code);
        inviteCode.setDate(create);

        inviteCodeRepository.save(inviteCode);

        LOGGER.info("Generate NEW inviteCode - " + inviteCode.toString());

        return inviteCode;
    }

    public InviteCode findByCode(String code) {
        Optional<InviteCode> byCode = inviteCodeRepository.findByCode(code);
        if (byCode.isEmpty()) throw new EntityNotFoundException(InviteCode.class, "code");
        return byCode.get();
    }

    public boolean validInvite(String email, String invite) {
        Optional<InviteCode> byEmail = inviteCodeRepository.findByEmail(email);
        if (byEmail.isEmpty()) return false;
        InviteCode inviteCode = byEmail.get();
        return inviteCode.getCode().equals(invite);
    }
}
