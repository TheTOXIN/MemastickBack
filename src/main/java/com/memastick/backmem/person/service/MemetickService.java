package com.memastick.backmem.person.service;

import com.memastick.backmem.errors.consts.ErrorCode;
import com.memastick.backmem.errors.exception.EntityNotFoundException;
import com.memastick.backmem.errors.exception.ValidationException;
import com.memastick.backmem.main.util.ValidationUtil;
import com.memastick.backmem.person.api.ChangeNickAPI;
import com.memastick.backmem.person.api.MemetickPreviewAPI;
import com.memastick.backmem.person.api.MemetickViewAPI;
import com.memastick.backmem.person.entity.Memetick;
import com.memastick.backmem.person.entity.User;
import com.memastick.backmem.person.repository.MemetickRepository;
import com.memastick.backmem.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public MemetickViewAPI viewByMe() {
        return mapToView(
            securityService.getCurrentUser().getMemetick()
        );
    }

    public MemetickViewAPI viewByLogin(String login) {
        return mapToView(
            userService.findMemetick(login)
        );
    }

    public MemetickPreviewAPI previewById(UUID id) {
        Memetick memetick = findById(id);

        return new MemetickPreviewAPI(
            memetick.getId(),
            memetick.getNick(),
            memetick.getDna()
        );
    }

    public void changeNick(ChangeNickAPI request) {
        if (!ValidationUtil.checkNick(request.getNick())) throw new ValidationException(ErrorCode.INVALID_NICK);

        Memetick memetick = securityService.getCurrentUser().getMemetick();
        memetick.setNick(request.getNick());
        memetickRepository.save(memetick);
    }

    public Memetick generateMemetick(String nick) {
        Memetick memetick = new Memetick();

        memetick.setNick(nick);

        return memetickRepository.save(memetick);
    }

    public Memetick findById(UUID id) {
        Optional<Memetick> byId = memetickRepository.findById(id);
        if (byId.isEmpty()) throw new EntityNotFoundException(Memetick.class, "id");
        return byId.get();
    }

    private MemetickViewAPI mapToView(Memetick memetick) {
        return new MemetickViewAPI(
            memetick.getId(),
            memetick.getNick()
        );
    }
}
