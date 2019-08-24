package com.memastick.backmem.user.repository;

import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.security.constant.RoleType;
import com.memastick.backmem.user.entity.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    @EntityGraph(value = "joinedMemetick")
    Optional<User> findByLogin(String login);

    @Cacheable(cacheNames = "findUserByLogin")
    @Query("SELECT u FROM User u WHERE u.login = :login")
    Optional<User> findByLoginWithCache(@Param("login") String login);

    Optional<User> findByRole(RoleType role);

    Optional<User> findByMemetickId(UUID memetickId);

    User findByMemetick(Memetick memetick);

    List<User> findByMemetickIn(List<Memetick> memeticks);
}

