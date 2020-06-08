package com.memastick.backmem.chat.repository;

import com.memastick.backmem.chat.enitity.ChatMessage;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepository extends PagingAndSortingRepository<ChatMessage, Long> {


}
