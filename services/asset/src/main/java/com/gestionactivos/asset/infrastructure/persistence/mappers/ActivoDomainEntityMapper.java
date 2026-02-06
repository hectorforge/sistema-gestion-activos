package com.gestionactivos.asset.infrastructure.persistence.mappers;

import com.gestionactivos.asset.domain.activo.Activo;
import com.gestionactivos.asset.infrastructure.persistence.activo.ActivoEntity;
import com.gestionactivos.asset.infrastructure.persistence.categoria.CategoriaEntity;
import org.springframework.stereotype.Component;

@Component
public class ActivoDomainEntityMapper {

    /**
     * Convierte un objeto de dominio Activo a su entidad JPA ActivoEntity.
     * @param activo
     * @return {@link ActivoEntity}
     */
    public ActivoEntity toEntity(Activo activo) {
        if (activo == null) {
            return null;
        }

        // Creamos una "entidad proxy" de categoría solo con el ID
        // Esto es suficiente para que JPA establezca la relación al guardar
        CategoriaEntity categoriaProxy = null;
        if (activo.getCategoriaId() != null) {
            categoriaProxy = CategoriaEntity.builder()
                    .idCategory(activo.getCategoriaId())
                    .build();
        }

        return ActivoEntity.builder()
                .idActivo(activo.getIdActivo())
                .codigoInventario(activo.getCodigoInventario())
                .nombreActivo(activo.getNombreActivo())
                .descripcion(activo.getDescripcion())
                .urlImgActivo(activo.getUrlImgActivo())
                .categoria(categoriaProxy)
                .estadoActual(activo.getEstadoActual())
                .ubicacionFisica(activo.getUbicacionFisica())
                .fechaIngreso(activo.getFechaIngreso())
                .valorReferencial(activo.getValorReferencial())
                .proveedor(activo.getProveedor())
                .vidaUtilMeses(activo.getVidaUtilMeses())
                .observaciones(activo.getObservaciones())
                .esActivo(activo.getEsActivo())
                .estaEliminado(activo.getEstaEliminado())
                .fechaCreacion(activo.getFechaCreacion())
                .fechaActualizacion(activo.getFechaActualizacion())
                .build();
    }

    /**
     * Convierte una entidad JPA ActivoEntity a su objeto de dominio Activo.
     * @param entity
     * @return {@link Activo}
     */
    public Activo toDomain(ActivoEntity entity) {
        if (entity == null) {
            return null;
        }

        return new Activo(
                entity.getIdActivo(),
                entity.getCodigoInventario(),
                entity.getNombreActivo(),
                entity.getDescripcion(),
                entity.getUrlImgActivo(),
                entity.getCategoria().getIdCategory(),
                entity.getEstadoActual(),
                entity.getUbicacionFisica(),
                entity.getFechaIngreso(),
                entity.getValorReferencial(),
                entity.getProveedor(),
                entity.getVidaUtilMeses(),
                entity.getObservaciones(),
                entity.getEsActivo(),
                entity.getEstaEliminado(),
                entity.getFechaCreacion(),
                entity.getFechaActualizacion()
        );
    }
}