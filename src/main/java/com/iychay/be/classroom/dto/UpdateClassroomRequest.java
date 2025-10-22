package com.iychay.be.classroom.dto;

import com.iychay.be.classroom.model.ClassroomStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record UpdateClassroomRequest(
        @Size(max = 150) String nombre,
        Long tutorId,
        @Size(max = 120) String horario,
        @Min(1) @Max(120) Integer capacidad,
        ClassroomStatus estado
) {
}
