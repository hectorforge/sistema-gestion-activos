package com.gestionactivos.asset.domain.activo.utils;

public enum EstadoActivo {
    OPERATIVO,        // Disponible para uso
    OCUPADO,          // Asignado / prestado / en uso
    EN_MANTENIMIENTO, // En reparación o revisión
    FUERA_DE_USO,     // No se usa pero aún existe
    DADO_DE_BAJA      // Retirado definitivamente
}