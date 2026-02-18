package com.gestionactivos.asset.infrastructure.persistence.mappers;

import com.gestionactivos.asset.domain.incidente.Incidente;
import com.gestionactivos.asset.infrastructure.persistence.incidente.IncidenteEntity;
import org.springframework.stereotype.Component;

@Component
public class IncidenteDomainEntityMapper {

    public IncidenteEntity toEntity(Incidente domain) {
        if (domain == null) return null;

        IncidenteEntity entity = new IncidenteEntity();
        entity.setIdIncidente(domain.getIdIncidente());
        entity.setIdActivo(domain.getIdActivo());
        entity.setIdUsuario(domain.getIdUsuario());
        entity.setTipoIncidente(domain.getTipoIncidente());
        entity.setDescripcion(domain.getDescripcion());
        entity.setFechaReporte(domain.getFechaReporte());
        entity.setEstadoIncidente(domain.getEstadoIncidente());

        return entity;
    }

    public Incidente toDomain(IncidenteEntity entity) {
        if (entity == null) return null;

        Incidente domain = new Incidente();
        domain.setIdIncidente(entity.getIdIncidente());
        domain.setIdActivo(entity.getIdActivo());
        domain.setIdUsuario(entity.getIdUsuario());
        domain.setTipoIncidente(entity.getTipoIncidente());
        domain.setDescripcion(entity.getDescripcion());
        domain.setFechaReporte(entity.getFechaReporte());
        domain.setEstadoIncidente(entity.getEstadoIncidente());

        return domain;
    }
}
