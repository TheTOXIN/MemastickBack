package com.memastick.backmem.chat.repository;

import com.memastick.backmem.chat.enitity.ChatMessage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface ChatMessageRepository extends PagingAndSortingRepository<ChatMessage, Long> {

    @Query("SELECT cm FROM ChatMessage cm WHERE cm.creating < :creating")
    List<ChatMessage> findBeforeCreating(@Param("creating") ZonedDateTime creating);

    @Query(
        value = "SELECT * FROM chat_messages cm ORDER BY cm.creating DESC LIMIT :count",
        nativeQuery = true
    )
    List<ChatMessage> finFirstByCount(@Param("count") int count);
}
