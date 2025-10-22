package com.iychay.be.message.service;

import com.iychay.be.message.dto.ConversationResponse;
import com.iychay.be.message.dto.CreateMessageRequest;
import com.iychay.be.message.dto.MessageResponse;
import com.iychay.be.message.model.ConversationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MessageService {

    Page<ConversationResponse> listConversations(ConversationType tipo, Long refId, Pageable pageable);

    Page<MessageResponse> listMessages(Long conversacionId, Pageable pageable);

    MessageResponse create(CreateMessageRequest request);
}
