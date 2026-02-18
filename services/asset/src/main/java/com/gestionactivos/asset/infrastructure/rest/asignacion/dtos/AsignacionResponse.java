package com.gestionactivos.asset.infrastructure.rest.asignacion.dtos;

public record AsignacionResponse(
        String idAsignacion,
        String idActivo,
        String idUsuario,
        String fechaAsignacion,
        String fechaDevolucion,
        String estadoAsignacion,
        String observaciones
) {}
