package com.memastick.backmem.code.repository;

import com.memastick.backmem.code.entity.InviteCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface InviteCodeRepository extends JpaRepository<InviteCode, UUID> {

    Optional<InviteCode> findByCode(String code);

    Optional<InviteCode> findByEmail(String email);

}
