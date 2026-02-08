package com.gestionactivos.asset.infrastructure.rest.activo.dtos;

import com.gestionactivos.asset.infrastructure.rest.activo.validators.ActualizarActivoGrupo;
import com.gestionactivos.asset.infrastructure.rest.activo.validators.CrearActivoGrupo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Request para crear un activo")
public record ActivoRequest(

        @Schema(
                description = "Identificador interno para la gestión del activo",
                defaultValue = "123e4567-e89b-12d3-a456-426614174000"
        )
        @NotBlank(message = "El Id del activo es necesario para la actualizacion", groups = ActualizarActivoGrupo.class)
        String id,

        @Schema(
                description = "Nombre del activo",
                defaultValue = "Laptop Dell Latitude 5420"
        )
        @NotBlank(message = "El nombre del activo es obligatorio")
        @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
        String nombreActivo,

        @Schema(
                description = "Descripción detallada del activo",
                defaultValue = "Laptop asignada al área de contabilidad"
        )
        String descripcion,

        @Schema(
                description = "URL de la imagen del activo",
                defaultValue = "https://cdn.midominio.com/activos/laptop-dell.png"
        )
        String urlImgActivo,

        @Schema(
                description = "Identificador de la categoría del activo",
                defaultValue = "22222222-2222-2222-2222-222222222222"
        )
        @NotBlank(message = "El ID de categoría es obligatorio")
        String categoriaId,

        @Schema(
                description = "Estado actual del activo",
                defaultValue = "OPERATIVO"
        )
        @NotBlank(message = "El estado del activo es obligatorio")
        String estadoActual,

        @Schema(
                description = "Ubicación física donde se encuentra el activo",
                defaultValue = "BIBLIOTECA"
        )
        @NotBlank(message = "La ubicación física es obligatoria")
        String ubicacionFisica,

        @Schema(
                description = "Fecha de ingreso del activo (YYYY-MM-DD)",
                defaultValue = "2025-01-15"
        )
        @NotBlank(message = "La fecha de ingreso es obligatoria")
        @Pattern(
                regexp = "^\\d{4}-\\d{2}-\\d{2}$",
                message = "Formato fecha inválido (YYYY-MM-DD)"
        )
        String fechaIngreso,

        @Schema(
                description = "Valor referencial del activo",
                defaultValue = "3500.00"
        )
        @NotBlank(message = "El valor es obligatorio")
        @Pattern(
                regexp = "^[0-9]+(\\.[0-9]{1,2})?$",
                message = "Debe ser un número decimal (ej: 100.00)"
        )
        String valorReferencial,

        @Schema(
                description = "Proveedor del activo",
                defaultValue = "Dell Perú S.A.C."
        )
        String proveedor,

        @Schema(
                description = "Vida útil del activo en meses",
                defaultValue = "36"
        )
        @Pattern(
                regexp = "^\\d+$",
                message = "La vida útil debe ser un número entero"
        )
        String vidaUtilMeses,

        @Schema(
                description = "Observaciones adicionales del activo",
                defaultValue = "Equipo nuevo con garantía de 3 años"
        )
        String observaciones
) {
}