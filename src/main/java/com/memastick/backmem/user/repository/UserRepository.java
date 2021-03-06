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

    @Override
    @EntityGraph(value = "joinedMemetick")
    List<User> findAll();

    @EntityGraph(value = "joinedMemetick")
    Optional<User> findByRole(RoleType role);

    @EntityGraph(value = "joinedMemetick")
    Optional<User> findByEmail(String email);

    @EntityGraph(value = "joinedMemetick")
    Optional<User> findByLogin(String login);

    @Cacheable(cacheNames = "findUserByLogin")
    @EntityGraph(value = "joinedMemetick")
    @Query("SELECT u FROM User u WHERE u.login = :login")
    Optional<User> findByLoginCache(@Param("login") String login);

    @EntityGraph(value = "joinedMemetick")
    User findByMemetickId(UUID memetickId);

    @EntityGraph(value = "joinedMemetick")
    User findByMemetick(Memetick memetick);

    @EntityGraph(value = "joinedMemetick")
    List<User> findByMemetickIn(List<Memetick> memeticks);

    default User tryFindByLogin(String login) {
        return this
            .findByLogin(login)
            .orElseThrow(() -> new EntityNotFoundException(User.class, "login"));
    }
}

