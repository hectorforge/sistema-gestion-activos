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

    ASIGNACION_NOT_FOUND("ASIGNACION_404", "La asignación solicitada no existe."),
    ASIGNACION_SAVE_ERROR("ASIGNACION_500", "Ocurrió un error al guardar la asignación."),
    ASIGNACION_UPDATE_ERROR("ASIGNACION_501", "No se pudo actualizar la asignación."),
    ASIGNACION_DELETE_ERROR("ASIGNACION_502", "No se pudo eliminar la asignación."),
    ASIGNACION_ID_MISMATCH("ASIGNACION_503", "El id del path no coincide con el del body."),
    ASIGNACION_LIST_ERROR("ASIGNACION_504", "Error al listar las asignaciones."),
    ASIGNACION_STATE_CHANGE_ERROR("ASIGNACION_505", "No se pudo cambiar el estado de la asignación."),

    INCIDENTE_NOT_FOUND("INCIDENTE_404", "El incidente solicitado no existe."),
    INCIDENTE_SAVE_ERROR("INCIDENTE_500", "Ocurrió un error al guardar el incidente."),
    INCIDENTE_UPDATE_ERROR("INCIDENTE_501", "No se pudo actualizar el incidente."),
    INCIDENTE_DELETE_ERROR("INCIDENTE_502", "No se pudo eliminar el incidente."),
    INCIDENTE_ID_MISMATCH("INCIDENTE_503", "El id del path no coincide con el del body."),
    INCIDENTE_LIST_ERROR("INCIDENTE_504", "Error al listar los incidentes."),
    INCIDENTE_STATE_CHANGE_ERROR("INCIDENTE_505", "No se pudo cambiar el estado del incidente."),
    INCIDENTE_TYPE_CHANGE_ERROR("INCIDENTE_506", "No se pudo cambiar el tipo del incidente."),


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