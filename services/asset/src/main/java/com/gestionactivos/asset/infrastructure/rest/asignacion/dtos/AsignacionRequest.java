package com.gestionactivos.asset.infrastructure.rest.asignacion.dtos;

import com.gestionactivos.asset.infrastructure.rest.asignacion.validators.ActualizarAsignacionGrupo;
import com.gestionactivos.asset.infrastructure.rest.asignacion.validators.CrearAsignacionGrupo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;

@Schema(description = "Request para crear/actualizar una asignación")
public record AsignacionRequest(

        @Schema(
                description = "ID de la asignación (solo para actualización)",
                defaultValue = "123e4567-e89b-12d3-a456-426614174000"
        )
        @NotBlank(
                groups = ActualizarAsignacionGrupo.class,
                message = "El ID es obligatorio para actualizar"
        )
        String id,

        @Schema(
                description = "ID del activo asignado",
                defaultValue = "11111111-1111-1111-1111-111111111111"
        )
        @NotBlank(message = "El ID del activo es obligatorio")
        String idActivo,

        @Schema(
                description = "ID del usuario al que se asigna",
                defaultValue = "22222222-2222-2222-2222-222222222222"
        )
        @NotBlank(message = "El ID del usuario es obligatorio")
        String idUsuario,

        @Schema(
                description = "Fecha de asignación (YYYY-MM-DD)",
                defaultValue = "2025-01-15"
        )
        @NotBlank(message = "La fecha de asignación es obligatoria")
        @Pattern(
                regexp = "^\\d{4}-\\d{2}-\\d{2}$",
                message = "Formato fecha inválido (YYYY-MM-DD)"
        )
        String fechaAsignacion,

        @Schema(
                description = "Fecha de devolución (YYYY-MM-DD)",
                defaultValue = "2025-02-15"
        )
        @Pattern(
                regexp = "^\\d{4}-\\d{2}-\\d{2}$",
                message = "Formato fecha inválido (YYYY-MM-DD)"
        )
        String fechaDevolucion,

        @Schema(
                description = "Estado de la asignación",
                defaultValue = "ACTIVA"
        )
        @NotBlank(message = "El estado es obligatorio")
        String estadoAsignacion,

        @Schema(
                description = "Observaciones",
                defaultValue = "Asignado al área de contabilidad"
        )
        String observaciones
) {}
