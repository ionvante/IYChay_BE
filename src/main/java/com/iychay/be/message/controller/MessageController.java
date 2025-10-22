package com.iychay.be.message.controller;

import com.iychay.be.message.dto.ConversationResponse;
import com.iychay.be.message.dto.CreateMessageRequest;
import com.iychay.be.message.dto.MessageResponse;
import com.iychay.be.message.model.ConversationType;
import com.iychay.be.message.service.MessageService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/conversaciones")
    public ResponseEntity<Page<ConversationResponse>> conversations(@RequestParam ConversationType tipo,
                                                                    @RequestParam(required = false) Long refId,
                                                                    Pageable pageable) {
        return ResponseEntity.ok(messageService.listConversations(tipo, refId, pageable));
    }

    @GetMapping("/mensajes")
    public ResponseEntity<Page<MessageResponse>> messages(@RequestParam Long conversacionId, Pageable pageable) {
        return ResponseEntity.ok(messageService.listMessages(conversacionId, pageable));
    }

    @PostMapping("/mensajes")
    public ResponseEntity<MessageResponse> create(@Valid @RequestBody CreateMessageRequest request) {
        return ResponseEntity.ok(messageService.create(request));
    }
}
