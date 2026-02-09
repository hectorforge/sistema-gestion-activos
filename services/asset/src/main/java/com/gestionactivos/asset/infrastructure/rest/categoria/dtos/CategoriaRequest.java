package com.gestionactivos.asset.infrastructure.rest.categoria.dtos;

import com.gestionactivos.asset.infrastructure.rest.categoria.validators.ActualizarCategoriaGrupo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Request para crear o actualizar una categoría")
public record CategoriaRequest(

        @Schema(
                description = "Identificador interno de la categoría",
                defaultValue = "123e4567-e89b-12d3-a456-426614174000"
        )
        @NotBlank(
                message = "El Id de la categoría es obligatorio para actualizar",
                groups = ActualizarCategoriaGrupo.class
        )
        String id,

        @Schema(
                description = "Nombre de la categoría",
                defaultValue = "Equipos de Cómputo"
        )
        @NotBlank(message = "El nombre de la categoría es obligatorio")
        @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
        String nombreCategoria,

        @Schema(
                description = "Abreviatura de 3 digitos de la categoria",
                defaultValue = "MON"
        )
        @NotBlank(message = "La abreviatura de la categoria es obligatoria")
        @Size(max = 3, message = "La abreviatura no puede exceder los 3 caracteres")
        String abreviaturaCategoria,

        @Schema(
                description = "Descripción de la categoría",
                defaultValue = "Categoría para laptops, desktops y accesorios"
        )
        String descripcionCategoria,

        @Schema(
                description = "URL de la imagen representativa de la categoría",
                defaultValue = "https://cdn.midominio.com/categorias/equipos.png"
        )
        String urlImgCategoria,

        @Schema(
                description = "Indica si la categoría está activa",
                defaultValue = "true"
        )
        String esActivo
) {}