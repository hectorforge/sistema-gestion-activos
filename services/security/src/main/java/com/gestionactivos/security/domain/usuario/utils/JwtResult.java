package com.gestionactivos.security.domain.usuario.utils;

public record JwtResult(
        String token,
        String email,
        String rol
)
{ }