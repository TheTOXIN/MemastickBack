package com.memastick.backmem.security.repository;

import com.memastick.backmem.security.entity.PasswordReset;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface PasswordResetRepository extends CrudRepository<PasswordReset, UUID> {

    Optional<PasswordReset> findByLogin(String login);

    Optional<PasswordReset> findByCode(String code);

}
