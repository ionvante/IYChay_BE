package com.iychay.be.message.repository;

import com.iychay.be.message.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
    Page<Message> findByConversationId(Long conversationId, Pageable pageable);
}
