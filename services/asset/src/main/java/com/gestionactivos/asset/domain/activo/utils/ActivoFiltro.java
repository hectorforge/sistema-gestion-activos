package com.gestionactivos.asset.domain.activo.utils;

import java.util.UUID;

public record ActivoFiltro(
        String nombre,
        String codigoInventario,
        UUID categoriaId,
        Boolean esActivo
){ }
