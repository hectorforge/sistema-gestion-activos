package com.gestionactivos.security.infrastructure.rest.usuario.dtos;

public record CambiarContrasenaRequest(
        String contrasenaActual,
        String nuevaContrasena
) {
}
