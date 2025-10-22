package com.iychay.be.student.dto;

import java.util.List;

public record StudentDetailResponse(
        Long id,
        String nombres,
        String apellidos,
        String dni,
        String contacto,
        Long aulaId,
        List<StudentHistoryItem> historial
) {
    public record StudentHistoryItem(
            String tipo,
            String descripcion
    ) {
    }
}
