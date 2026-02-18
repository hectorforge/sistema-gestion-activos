package com.gestionactivos.asset.infrastructure.persistence.mappers;

import com.gestionactivos.asset.domain.asignacion.Asignacion;
import com.gestionactivos.asset.infrastructure.persistence.asignacion.AsignacionEntity;
import org.springframework.stereotype.Service;

@Service
public class AsignacionDomainEntityMapper {
    public AsignacionEntity toEntity(Asignacion domain) {
        if (domain == null) return null;

        return AsignacionEntity.builder()
                .idAsignacion(domain.getIdAsignacion())
                .idActivo(domain.getIdActivo())
                .idUsuario(domain.getIdUsuario())
                .fechaAsignacion(domain.getFechaAsignacion())
                .fechaDevolucion(domain.getFechaDevolucion())
                .estadoAsignacion(domain.getEstadoAsignacion())
                .observaciones(domain.getObservaciones())
                .build();
    }

    public Asignacion toDomain(AsignacionEntity entity) {
        if (entity == null) return null;

        return new Asignacion(
                entity.getIdAsignacion(),
                entity.getIdActivo(),
                entity.getIdUsuario(),
                entity.getFechaAsignacion(),
                entity.getFechaDevolucion(),
                entity.getEstadoAsignacion(),
                entity.getObservaciones()
        );
    }
}
