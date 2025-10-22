package com.iychay.be.message.dto;

import com.iychay.be.message.model.ConversationType;
import java.util.List;

public record ConversationResponse(
        Long id,
        ConversationType tipo,
        Long refId,
        List<Long> participantes
) {
}
