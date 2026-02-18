package com.gestionactivos.asset.infrastructure.rest.incidente.mappers;

import com.gestionactivos.asset.domain.incidente.Incidente;
import com.gestionactivos.asset.domain.incidente.utils.EstadoIncidente;
import com.gestionactivos.asset.domain.incidente.utils.TipoIncidente;
import com.gestionactivos.asset.infrastructure.rest.incidente.dtos.IncidenteRequest;
import com.gestionactivos.asset.infrastructure.rest.incidente.dtos.IncidenteResponse;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class IncidenteDtoEntityMapper {

    public Incidente toDomain(IncidenteRequest request) {
        if (request == null) return null;

        Incidente incidente = new Incidente();

        if (request.idIncidente() != null && !request.idIncidente().isBlank())
            incidente.setIdIncidente(UUID.fromString(request.idIncidente()));

        incidente.setIdActivo(UUID.fromString(request.idActivo()));
        incidente.setIdUsuario(UUID.fromString(request.idUsuario()));
        incidente.setTipoIncidente(TipoIncidente.valueOf(request.tipoIncidente()));
        incidente.setDescripcion(request.descripcion());

        if (request.estadoIncidente() != null && !request.estadoIncidente().isBlank()) {
            incidente.setEstadoIncidente(EstadoIncidente.valueOf(request.estadoIncidente()));
        }

        return incidente;
    }

    public IncidenteResponse toResponse(Incidente domain) {
        if (domain == null) return null;

        return new IncidenteResponse(
                domain.getIdIncidente() != null ? domain.getIdIncidente().toString() : null,
                domain.getIdActivo() != null ? domain.getIdActivo().toString() : null,
                domain.getIdUsuario() != null ? domain.getIdUsuario().toString() : null,
                domain.getTipoIncidente() != null ? domain.getTipoIncidente().name() : null,
                domain.getDescripcion(),
                domain.getFechaReporte() != null
                        ? domain.getFechaReporte().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                        : null,
                domain.getEstadoIncidente() != null ? domain.getEstadoIncidente().name() : null
        );
    }
}