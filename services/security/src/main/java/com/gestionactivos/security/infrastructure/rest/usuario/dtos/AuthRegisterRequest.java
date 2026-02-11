package com.gestionactivos.security.infrastructure.rest.usuario.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos para registrar un nuevo usuario")
public record AuthRegisterRequest(
        @Schema(description = "Nombre del usuario", example = "Juan", required = true)
        String nombre,

        @Schema(description = "Apellido del usuario", example = "Pérez", required = true)
        String apellido,

        @Schema(description = "Correo electrónico único del usuario", example = "juan@example.com", required = true)
        String email,

        @Schema(description = "Contraseña del usuario", example = "P@ssw0rd!", required = true)
        String password,

        @Schema(description = "URL de la foto de perfil del usuario", example = "https://example.com/foto.jpg", required = false)
        String fotoDePerfil
) {}
