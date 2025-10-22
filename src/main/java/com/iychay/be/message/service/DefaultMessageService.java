package com.iychay.be.message.service;

import com.iychay.be.message.dto.ConversationResponse;
import com.iychay.be.message.dto.CreateMessageRequest;
import com.iychay.be.message.dto.MessageResponse;
import com.iychay.be.message.model.Conversation;
import com.iychay.be.message.model.ConversationType;
import com.iychay.be.message.model.Message;
import com.iychay.be.message.repository.ConversationRepository;
import com.iychay.be.message.repository.MessageRepository;
import com.iychay.be.user.repository.UserRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DefaultMessageService implements MessageService {

    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public DefaultMessageService(ConversationRepository conversationRepository,
                                 MessageRepository messageRepository,
                                 UserRepository userRepository) {
        this.conversationRepository = conversationRepository;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Page<ConversationResponse> listConversations(ConversationType tipo, Long refId, Pageable pageable) {
        List<Conversation> conversations = refId != null
                ? conversationRepository.findByTipoAndRefId(tipo, refId)
                : conversationRepository.findByTipo(tipo);
        var mapped = conversations.stream().map(this::toResponse).toList();
        return new PageImpl<>(mapped, pageable, mapped.size());
    }

    @Override
    public Page<MessageResponse> listMessages(Long conversacionId, Pageable pageable) {
        return messageRepository.findByConversationId(conversacionId, pageable).map(this::toResponse);
    }

    @Override
    public MessageResponse create(CreateMessageRequest request) {
        Conversation conversation = conversationRepository.findById(request.conversacionId())
                .orElseThrow(() -> new IllegalArgumentException("Conversación no encontrada"));
        var user = userRepository.findById(request.emisorId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario emisor no encontrado"));
        Message message = new Message();
        message.setConversation(conversation);
        message.setEmisor(user);
        message.setTexto(request.texto());
        message.setAdjuntoUrl(request.adjuntoUrl());
        return toResponse(messageRepository.save(message));
    }

    private ConversationResponse toResponse(Conversation conversation) {
        return new ConversationResponse(
                conversation.getId(),
                conversation.getTipo(),
                conversation.getRefId(),
                conversation.getParticipantes()
        );
    }

    private MessageResponse toResponse(Message message) {
        return new MessageResponse(
                message.getId(),
                message.getConversation().getId(),
                message.getEmisor().getId(),
                message.getTexto(),
                message.getAdjuntoUrl(),
                message.getFecha()
        );
    }
}
