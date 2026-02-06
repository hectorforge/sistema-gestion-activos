package com.gestionactivos.asset.infrastructure.rest.activo.dtos;

import java.util.UUID;

public record ActivoResponse(
        String idActivo,
        String codigoInventario,
        String nombreActivo,
        String descripcion,
        String urlImgActivo,
        String categoriaId,
        String estadoActual,
        String ubicacionFisica,
        String fechaIngreso,
        String valorReferencial,
        String proveedor,
        String vidaUtilMeses,
        String observaciones,
        String esActivo,
        String fechaCreacion,
        String fechaActualizacion
) {}