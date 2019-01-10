package com.memastick.backmem.person.repository;

import com.memastick.backmem.security.constant.RoleType;
import com.memastick.backmem.person.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface UserRepository extends CrudRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    Optional<User> findByRole(RoleType role);

}

