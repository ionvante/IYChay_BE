package com.iychay.be.student.dto;

public record StudentResponse(
        Long id,
        String nombres,
        String apellidos,
        String dni,
        Long aulaId
) {
}
