package com.iychay.be.classroom.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateClassroomRequest(
        @NotBlank @Size(max = 150) String nombre,
        Long tutorId,
        @Size(max = 120) String horario,
        @NotNull @Min(1) @Max(120) Integer capacidad
) {
}
