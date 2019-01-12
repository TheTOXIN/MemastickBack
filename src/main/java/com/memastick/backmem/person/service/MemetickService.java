package com.memastick.backmem.person.service;

import com.memastick.backmem.person.api.MemetickAPI;
import com.memastick.backmem.person.entity.Memetick;
import com.memastick.backmem.person.repository.MemetickRepository;
import com.memastick.backmem.security.model.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class MemetickService {

    private final MemetickRepository memetickRepository;

    @Autowired
    public MemetickService(
        MemetickRepository memetickRepository
    ) {
        this.memetickRepository = memetickRepository;
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

    public Memetick generateMemetick(String nick) {
        Memetick memetick = new Memetick();

        memetick.setNick(nick);

        return memetickRepository.save(memetick);
    }

}
