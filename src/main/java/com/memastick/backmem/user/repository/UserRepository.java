package com.memastick.backmem.user.repository;

import com.memastick.backmem.security.constant.RoleType;
import com.memastick.backmem.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    Optional<User> findByLogin(String login);

    Optional<User> findByRole(RoleType role);

    Optional<User> findByMemetickId(UUID memetickId);
}

