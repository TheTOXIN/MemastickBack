package com.memastick.backmem.memetick.service;

import com.memastick.backmem.errors.consts.ErrorCode;
import com.memastick.backmem.errors.exception.EntityNotFoundException;
import com.memastick.backmem.errors.exception.SettingException;
import com.memastick.backmem.errors.exception.ValidationException;
import com.memastick.backmem.main.util.ValidationUtil;
import com.memastick.backmem.memetick.api.ChangeNickAPI;
import com.memastick.backmem.memetick.api.MemetickAPI;
import com.memastick.backmem.memetick.dto.MemetickPreviewDTO;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.repository.MemetickRepository;
import com.memastick.backmem.security.service.SecurityService;
import com.memastick.backmem.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;


@Service
public class MemetickService {

    private final MemetickRepository memetickRepository;
    private final SecurityService securityService;
    private final UserService userService;

    @Autowired
    public MemetickService(
        MemetickRepository memetickRepository,
        SecurityService securityService,
        UserService userService
    ) {
        this.memetickRepository = memetickRepository;
        this.securityService = securityService;
        this.userService = userService;
    }

    public MemetickAPI viewByMe() {
        return mapToView(securityService.getCurrentMemetick());
    }

    public MemetickAPI viewById(UUID id) {
        return mapToView(findById(id));
    }

    public MemetickPreviewDTO previewById(UUID id) {
        Memetick memetick = findById(id);

        return new MemetickPreviewDTO(
            memetick.getId(),
            memetick.getNick()
        );
    }

    public void addDna(Memetick memetick, int dna) {
        memetick.setDna(memetick.getDna() + dna);
        memetickRepository.save(memetick);
    }

    public void changeNick(ChangeNickAPI request) {
        if (!ValidationUtil.checkNick(request.getNick())) throw new ValidationException(ErrorCode.INVALID_NICK);

        Memetick memetick = securityService.getCurrentMemetick();

        if (memetick.getNickChanged().plusWeeks(1).isAfter(ZonedDateTime.now()))
            throw new SettingException(ErrorCode.EXPIRE_NICK);

        memetick.setNick(request.getNick());
        memetick.setNickChanged(ZonedDateTime.now());

        memetickRepository.save(memetick);
    }

    public Memetick findById(UUID id) {
        Optional<Memetick> byId = memetickRepository.findById(id);
        if (byId.isEmpty()) throw new EntityNotFoundException(Memetick.class, "id");
        return byId.get();
    }

    private MemetickAPI mapToView(Memetick memetick) {
        return new MemetickAPI(
            memetick.getId(),
            memetick.getNick()
        );
    }

}
