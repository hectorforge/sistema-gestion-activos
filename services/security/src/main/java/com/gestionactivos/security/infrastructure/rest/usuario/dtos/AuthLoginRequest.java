package com.gestionactivos.security.infrastructure.rest.usuario.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos para iniciar sesión")
public record AuthLoginRequest(
        @Schema(description = "Correo electrónico del usuario", example = "isagar@gmail.com", required = true)
        String email,

        @Schema(description = "Contraseña del usuario", example = "user123", required = true)
        String password
) {}
