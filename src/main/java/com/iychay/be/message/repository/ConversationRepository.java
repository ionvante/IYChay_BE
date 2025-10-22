package com.iychay.be.message.repository;

import com.iychay.be.message.model.Conversation;
import com.iychay.be.message.model.ConversationType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    List<Conversation> findByTipoAndRefId(ConversationType tipo, Long refId);

    List<Conversation> findByTipo(ConversationType tipo);
}
