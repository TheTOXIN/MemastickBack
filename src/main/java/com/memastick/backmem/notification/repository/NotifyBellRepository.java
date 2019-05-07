package com.memastick.backmem.notification.repository;

import com.memastick.backmem.notification.entity.NotifyBell;
import com.memastick.backmem.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NotifyBellRepository extends JpaRepository<NotifyBell, UUID> {

    List<NotifyBell> findAllByUser(User user);

    void deleteAllByUser(User user);

    Optional<Long> countByUserAndIsReadFalse(User user);

    @Query("UPDATE NotifyBell nb SET nb.read = true")
    void markAsRead(NotifyBell b);
}
