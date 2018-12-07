package com.memastick.backmem.invite.repository;

import com.memastick.backmem.invite.entity.Invite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface InviteRepository extends JpaRepository<Invite, Long> {

    Optional<Invite> findByCode(String code);

}
