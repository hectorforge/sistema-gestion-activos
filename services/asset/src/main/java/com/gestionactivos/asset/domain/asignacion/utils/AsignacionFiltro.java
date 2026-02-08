package com.gestionactivos.asset.domain.asignacion.utils;

import java.util.UUID;

public record AsignacionFiltro(
        UUID idActivo,
        UUID idUsuario,
        EstadoAsignacion estado
) {
}