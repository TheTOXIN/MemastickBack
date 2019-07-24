package com.memastick.backmem.security.service;

import com.memastick.backmem.errors.consts.ErrorCode;
import com.memastick.backmem.errors.exception.EmailNotSendException;
import com.memastick.backmem.errors.exception.EntityNotFoundException;
import com.memastick.backmem.errors.exception.TimeInException;
import com.memastick.backmem.errors.exception.ValidationException;
import com.memastick.backmem.main.constant.TimeConstant;
import com.memastick.backmem.main.util.ValidationUtil;
import com.memastick.backmem.security.api.InviteCodeAPI;
import com.memastick.backmem.security.entity.InviteCode;
import com.memastick.backmem.security.repository.InviteCodeRepository;
import com.memastick.backmem.sender.dto.EmailStatus;
import com.memastick.backmem.sender.service.SenderInviteCodeService;
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
        InviteCode invite = inviteCodeRepository
            .findByEmail(request.getEmail())
            .orElseGet(() -> generateInvite(request));

        invite.setNick(request.getNick());

        if (autoInvite) {
            if (invite.getDateSend().plusHours(1).isAfter(LocalDateTime.now())) throw new TimeInException("INVITE WAITE SEND");
            EmailStatus status = send(invite);
            if (status.isError()) throw new EmailNotSendException(status.getEmail().getTo());
        }
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
            inviteCode.setDateSend(LocalDateTime.now());

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
        inviteCode.setDateCreate(create);
        inviteCode.setDateSend(TimeConstant.START_LOCAL_TIME);

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
