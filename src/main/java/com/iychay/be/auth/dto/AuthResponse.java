package com.iychay.be.auth.dto;

import com.iychay.be.user.model.Role;

public record AuthResponse(
        String token,
        Role rol,
        String nombre
) {
}
