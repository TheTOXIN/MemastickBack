package com.memastick.backmem.invite.repository;

import com.memastick.backmem.invite.entity.Invite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface InviteRepository extends JpaRepository<Invite, Long> {

    Invite findByEmail(String email);

}
