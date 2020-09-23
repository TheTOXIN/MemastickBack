package com.memastick.backmem.donate.repository;

import com.memastick.backmem.donate.entity.DonateMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonateMessageRepository extends JpaRepository<DonateMessage, Long> {

}
