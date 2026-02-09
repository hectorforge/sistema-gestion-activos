package com.gestionactivos.asset.infrastructure.rest.categoria.mappers;

import com.gestionactivos.asset.domain.categoria.Categoria;
import com.gestionactivos.asset.infrastructure.rest.categoria.dtos.CategoriaRequest;
import com.gestionactivos.asset.infrastructure.rest.categoria.dtos.CategoriaResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoriaDtoEntityMapper {

    public Categoria toDomain(CategoriaRequest request) {
        if (request == null) return null;

        Categoria categoria = new Categoria();

        if (request.id() != null && !request.id().isBlank())
            categoria.setIdCategoria(UUID.fromString(request.id()));

        categoria.setNombreCategoria(request.nombreCategoria());
        categoria.setAbreviaturaCategoria(request.abreviaturaCategoria());
        categoria.setDescripcionCategoria(request.descripcionCategoria());
        categoria.setUrlImgCategoria(request.urlImgCategoria());

        if (request.esActivo() != null)
            categoria.setEsActivo(Boolean.valueOf(request.esActivo()));

        return categoria;
    }

    public CategoriaResponse toResponse(Categoria domain) {
        if (domain == null) return null;

        return new CategoriaResponse(
                domain.getIdCategoria() != null ? domain.getIdCategoria().toString() : null,
                domain.getNombreCategoria(),
                domain.getAbreviaturaCategoria(),
                domain.getDescripcionCategoria(),
                domain.getUrlImgCategoria(),
                domain.getEsActivo() != null ? domain.getEsActivo().toString() : null,
                domain.getEstaEliminado() != null ? domain.getEstaEliminado().toString() : null
        );
    }
}