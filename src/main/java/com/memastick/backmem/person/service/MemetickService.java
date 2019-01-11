package com.memastick.backmem.person.service;

import com.memastick.backmem.person.api.MemetickAPI;
import com.memastick.backmem.person.entity.Memetick;
import com.memastick.backmem.security.model.MyUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class MemetickService {

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

}
