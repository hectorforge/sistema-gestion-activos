package com.gestionactivos.security.infrastructure.rest.usuario.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos para actualizar perfil de usuario")
public record UsuarioProfileRequest(
        @Schema(description = "Nombre del usuario", example = "Juan", required = true)
        String nombre,

        @Schema(description = "Apellido del usuario", example = "Pérez", required = true)
        String apellido,

        @Schema(description = "URL de la foto de perfil del usuario", example = "https://example.com/foto.jpg", required = false)
        String fotoDePerfil
) {}
