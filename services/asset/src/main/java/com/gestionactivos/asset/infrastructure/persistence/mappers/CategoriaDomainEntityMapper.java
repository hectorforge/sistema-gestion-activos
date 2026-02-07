package com.gestionactivos.asset.infrastructure.persistence.mappers;

import com.gestionactivos.asset.domain.categoria.Categoria;
import com.gestionactivos.asset.infrastructure.persistence.categoria.CategoriaEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoriaDomainEntityMapper {

    public CategoriaEntity toEntity(Categoria domain) {
        if (domain == null) return null;

        CategoriaEntity entity = new CategoriaEntity();
        entity.setIdCategory(domain.getIdCategoria());
        entity.setNombreCategoria(domain.getNombreCategoria());
        entity.setAbreviaturaCategoria(domain.getAbreviaturaCategoria());
        entity.setDescripcionCategoria(domain.getDescripcionCategoria());
        entity.setUrlImgCategoria(domain.getUrlImgCategoria());
        entity.setEsActivo(domain.getEsActivo());
        entity.setEstaEliminado(domain.getEstaEliminado());
        return entity;
    }

    public Categoria toDomain(CategoriaEntity entity) {
        if (entity == null) return null;

        Categoria domain = new Categoria();
        domain.setIdCategoria(entity.getIdCategory());
        domain.setNombreCategoria(entity.getNombreCategoria());
        domain.setAbreviaturaCategoria(entity.getAbreviaturaCategoria());
        domain.setDescripcionCategoria(entity.getDescripcionCategoria());
        domain.setUrlImgCategoria(entity.getUrlImgCategoria());
        domain.setEsActivo(entity.getEsActivo());
        domain.setEstaEliminado(entity.getEstaEliminado());
        return domain;
    }
}