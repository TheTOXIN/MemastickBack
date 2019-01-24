package com.memastick.backmem.person.service;

import com.memastick.backmem.errors.consts.ErrorCode;
import com.memastick.backmem.errors.exception.EntityNotFoundException;
import com.memastick.backmem.errors.exception.ValidationException;
import com.memastick.backmem.main.util.ValidationUtil;
import com.memastick.backmem.person.api.ChangeNickAPI;
import com.memastick.backmem.person.api.MemetickAPI;
import com.memastick.backmem.person.entity.Memetick;
import com.memastick.backmem.person.repository.MemetickRepository;
import com.memastick.backmem.security.model.MyUserDetails;
import com.memastick.backmem.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
public class MemetickService {

    private final MemetickRepository memetickRepository;
    private final SecurityService securityService;

    @Autowired
    public MemetickService(
        MemetickRepository memetickRepository,
        SecurityService securityService
    ) {
        this.memetickRepository = memetickRepository;
        this.securityService = securityService;
    }

    public MemetickAPI me() {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getPrincipal();

        Memetick memetick = userDetails.getMemetick();

        return new MemetickAPI(
            memetick.getId(),
            memetick.getNick()
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
}
