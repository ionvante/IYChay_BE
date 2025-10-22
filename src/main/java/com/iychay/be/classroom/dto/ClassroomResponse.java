package com.iychay.be.classroom.dto;

import com.iychay.be.classroom.model.ClassroomStatus;

public record ClassroomResponse(
        Long id,
        String nombre,
        Long tutorId,
        String horario,
        Integer capacidad,
        ClassroomStatus estado
) {
}
