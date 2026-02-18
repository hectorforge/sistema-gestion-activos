package com.gestionactivos.asset.infrastructure.rest.incidente.dtos;

import com.gestionactivos.asset.infrastructure.rest.incidente.validators.ActualizarIncidenteGrupo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Request para crear o actualizar un incidente")
public record IncidenteRequest(

        @Schema(
                description = "Identificador interno del incidente",
                defaultValue = "123e4567-e89b-12d3-a456-426614174000"
        )
        @NotBlank(
                message = "El Id del incidente es obligatorio para actualizar",
                groups = ActualizarIncidenteGrupo.class
        )
        String idIncidente,

        @Schema(
                description = "Identificador del activo relacionado",
                defaultValue = "123e4567-e89b-12d3-a456-426614174111"
        )
        @NotBlank(message = "El Id del activo es obligatorio")
        String idActivo,

        @Schema(
                description = "Identificador del usuario que reporta",
                defaultValue = "123e4567-e89b-12d3-a456-426614174222"
        )
        @NotBlank(message = "El Id del usuario es obligatorio")
        String idUsuario,

        @Schema(
                description = "Tipo de incidente",
                defaultValue = "DANIO"
        )
        @NotBlank(message = "El tipo de incidente es obligatorio")
        String tipoIncidente,

        @Schema(
                description = "Descripción detallada del incidente",
                defaultValue = "El equipo presenta fallas al encender"
        )
        @NotBlank(message = "La descripción es obligatoria")
        @Size(max = 1000, message = "La descripción no puede exceder los 1000 caracteres")
        String descripcion,

        @Schema(
                description = "Estado actual del incidente",
                defaultValue = "PENDIENTE"
        )
        String estadoIncidente
) {}
