package com.iychay.be.auth.dto;

import com.iychay.be.user.model.Role;

public record ProfileResponse(
        Long id,
        Role rol,
        String nombre,
        String email
) {
}
