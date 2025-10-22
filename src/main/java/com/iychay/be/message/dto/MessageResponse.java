package com.iychay.be.message.dto;

import java.time.OffsetDateTime;

public record MessageResponse(
        Long id,
        Long conversacionId,
        Long emisorId,
        String texto,
        String adjuntoUrl,
        OffsetDateTime fecha
) {
}
