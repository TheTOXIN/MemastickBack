package com.memastick.backmem.user.repository;

import com.memastick.backmem.errors.exception.EntityNotFoundException;
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

    Optional<User> findByRole(RoleType role);

    @EntityGraph(value = "joinedMemetick")
    Optional<User> findByLogin(String login);

    @Cacheable(cacheNames = "findUserByLogin")
    @Query("SELECT u FROM User u WHERE u.login = :login")
    Optional<User> findByLoginCache(@Param("login") String login);

    @Cacheable(cacheNames = "findUserByMemetickId")
    @EntityGraph(value = "joinedMemetick")
    User findByMemetickId(UUID memetickId);

    @Cacheable(cacheNames = "findUserByMemetick")
    @EntityGraph(value = "joinedMemetick")
    User findByMemetick(Memetick memetick);

    @Cacheable(cacheNames = "findUserByMemetickIn")
    @EntityGraph(value = "joinedMemetick")
    List<User> findByMemetickIn(List<Memetick> memeticks);

    default User tryFindByLoginCache(String login) {
        return this
            .findByLoginCache(login)
            .orElseThrow(() -> new EntityNotFoundException(User.class, "login"));
    }

    default User tryFindByLogin(String login) {
        return this
            .findByLogin(login)
            .orElseThrow(() -> new EntityNotFoundException(User.class, "login"));
    }
}

