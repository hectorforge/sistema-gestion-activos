package com.gestionactivos.asset.infrastructure.rest.categoria.dtos;

import java.util.UUID;

public record CategoriaRequest(
        String idCategory,
        String nombreCategoria,
        String descripcionCategoria,
        String urlImgCategoria
        //boolean esActivo;
        //boolean estaEliminado;
)
{}
