package com.gestionactivos.asset.domain.common;

public enum ErrorCatalog {
    ACTIVO_NOT_FOUND("ACTIVO_404", "El activo solicitado no existe o ha sido eliminado."),
    ACTIVO_INVALID_DATA("ACTIVO_400", "Los datos del activo son inválidos o están incompletos."),
    ACTIVO_SAVE_ERROR("ACTIVO_500", "Ocurrió un error al intentar guardar el activo."),
    ACTIVO_UPDATE_ERROR("ACTIVO_501", "No se pudo actualizar el activo (posiblemente no existe)."),
    ACTIVO_ID_MISMATCH("ACTIVO_502", "El id del path no coincide con el del body."),

    CATEGORIA_NOT_FOUND("CATEGORIA_404", "La categoría solicitada no existe o ha sido eliminada."),
    CATEGORIA_INVALID_DATA("CATEGORIA_400", "Los datos de la categoría son inválidos o están incompletos."),
    CATEGORIA_SAVE_ERROR("CATEGORIA_500", "Ocurrió un error al intentar guardar la categoría."),
    CATEGORIA_UPDATE_ERROR("CATEGORIA_501", "No se pudo actualizar la categoría (posiblemente no existe)."),
    CATEGORIA_DELETE_ERROR("CATEGORIA_502", "Ocurrió un error al intentar eliminar la categoría."),
    CATEGORIA_ID_MISMATCH("CATEGORIA_503", "El id del path no coincide con el del body."),

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