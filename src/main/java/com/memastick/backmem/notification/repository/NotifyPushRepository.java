package com.memastick.backmem.notification.repository;

import com.memastick.backmem.notification.entity.NotifyPush;
import com.memastick.backmem.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NotifyPushRepository extends JpaRepository<NotifyPush, UUID> {

    Optional<NotifyPush> findByToken(String token);

    List<NotifyPush> findAllByUser(User user);
}
