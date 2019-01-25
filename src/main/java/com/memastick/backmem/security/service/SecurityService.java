package com.memastick.backmem.security.service;

import com.memastick.backmem.errors.exception.EntityNotFoundException;
import com.memastick.backmem.person.entity.Memetick;
import com.memastick.backmem.person.repository.MemetickRepository;
import com.memastick.backmem.security.model.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SecurityService {

    private final MemetickRepository memetickRepository;

    @Autowired
    public SecurityService(
        MemetickRepository memetickRepository
    ) {
        this.memetickRepository = memetickRepository;
    }

    public MyUserDetails getCurrentUser() {
        return (MyUserDetails) SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getPrincipal();
    }

    public Memetick getCurrentMemetick() {
        MyUserDetails userDetails = getCurrentUser();
        Optional<Memetick> byId = memetickRepository.findById(userDetails.getMemetick().getId());
        if (byId.isEmpty()) throw new EntityNotFoundException(Memetick.class, "id");
        return byId.get();
    }

}
