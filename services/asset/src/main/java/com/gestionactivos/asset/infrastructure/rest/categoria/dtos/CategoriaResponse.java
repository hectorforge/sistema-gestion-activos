package com.gestionactivos.asset.infrastructure.rest.categoria.dtos;

public record CategoriaResponse(
        String idCategoria,
        String nombreCategoria,
        String abreviaturaCategoria,
        String descripcionCategoria,
        String urlImgCategoria,
        String esActivo,
        String estaEliminado
) {}