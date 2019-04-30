package com.memastick.backmem.notification.repository;

import com.memastick.backmem.notification.entity.NotifyBell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NotifyBellRepository extends JpaRepository<NotifyBell, UUID> {



}
