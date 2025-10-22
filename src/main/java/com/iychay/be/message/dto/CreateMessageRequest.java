package com.iychay.be.message.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateMessageRequest(
        @NotNull Long conversacionId,
        @NotNull Long emisorId,
        @NotBlank String texto,
        String adjuntoUrl
) {
}
