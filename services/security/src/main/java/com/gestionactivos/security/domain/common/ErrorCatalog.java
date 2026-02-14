package com.gestionactivos.security.domain.common;

public enum ErrorCatalog {
    USUARIO_NOT_FOUND("USUARIO_404", "El usuario no fue encontrado."),
    USUARIO_EMAIL_DUPLICADO("USUARIO_409", "El email ya está registrado."),
    USUARIO_NOT_MATCH("USUARIO_402", "El id del usuario a editar no coincide con el id del usuario enviado"),
    USUARIO_INACTIVO("USUARIO_403", "El usuario está inactivo o eliminado."),
    CONTRASENA_INCORRECTA("PASSWORD_401", "La contraseña actual es incorrecta."),
    JWT_GENERATION_ERROR("JWT_500", "No se pudo generar el token JWT."),
    JWT_INVALID("JWT_401", "Token JWT inválido o expirado."),

    GENERIC_ERROR("GENERIC_500", "Ocurrió un error inesperado en el sistema.");
    private final String errorCode;
    private final String errorMessage;

    ErrorCatalog(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}