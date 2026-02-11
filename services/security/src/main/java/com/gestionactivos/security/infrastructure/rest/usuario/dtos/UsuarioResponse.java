package com.gestionactivos.security.infrastructure.rest.usuario.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Datos de un usuario para respuestas al cliente")
public record UsuarioResponse(
        @Schema(description = "ID único del usuario", example = "f47ac10b-58cc-4372-a567-0e02b2c3d479")
        UUID id,

        @Schema(description = "Nombre del usuario", example = "Juan")
        String nombre,

        @Schema(description = "Apellido del usuario", example = "Pérez")
        String apellido,

        @Schema(description = "Correo electrónico del usuario", example = "juan@example.com")
        String email,

        @Schema(description = "URL de la foto de perfil del usuario", example = "https://example.com/foto.jpg")
        String fotoDePerfil,

        @Schema(description = "Indica si el usuario está activo", example = "true")
        boolean esActivo,

        @Schema(description = "Rol del usuario", example = "USER")
        String rol
) {}
