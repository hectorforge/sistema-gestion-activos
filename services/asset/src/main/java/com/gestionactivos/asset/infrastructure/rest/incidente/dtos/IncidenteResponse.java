package com.gestionactivos.asset.infrastructure.rest.incidente.dtos;

public record IncidenteResponse(
        String idIncidente,
        String idActivo,
        String idUsuario,
        String tipoIncidente,
        String descripcion,
        String fechaReporte,
        String estadoIncidente
) {}