package com.iychay.be.student.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record StudentRequest(
        @NotBlank @Size(max = 120) String nombres,
        @NotBlank @Size(max = 120) String apellidos,
        @Size(max = 15) String dni,
        @Size(max = 120) String contacto,
        Long aulaId
) {
}
