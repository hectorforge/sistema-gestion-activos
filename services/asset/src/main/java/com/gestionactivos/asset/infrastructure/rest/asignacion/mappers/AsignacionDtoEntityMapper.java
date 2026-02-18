package com.gestionactivos.asset.infrastructure.rest.asignacion.mappers;

import com.gestionactivos.asset.domain.asignacion.Asignacion;
import com.gestionactivos.asset.domain.asignacion.utils.EstadoAsignacion;
import com.gestionactivos.asset.infrastructure.rest.asignacion.dtos.AsignacionRequest;
import com.gestionactivos.asset.infrastructure.rest.asignacion.dtos.AsignacionResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class AsignacionDtoEntityMapper {

    public Asignacion toDomain(AsignacionRequest request) {
        if (request == null) return null;

        Asignacion asignacion = new Asignacion();

        if (request.id() != null && !request.id().isBlank())
            asignacion.setIdAsignacion(UUID.fromString(request.id()));

        asignacion.setIdActivo(UUID.fromString(request.idActivo()));
        asignacion.setIdUsuario(UUID.fromString(request.idUsuario()));
        asignacion.setFechaAsignacion(LocalDate.parse(request.fechaAsignacion()));

        if (request.fechaDevolucion() != null && !request.fechaDevolucion().isBlank())
            asignacion.setFechaDevolucion(LocalDate.parse(request.fechaDevolucion()));

        asignacion.setEstadoAsignacion(EstadoAsignacion.valueOf(request.estadoAsignacion()));
        asignacion.setObservaciones(request.observaciones());

        return asignacion;
    }

    public AsignacionResponse toResponse(Asignacion domain) {
        if (domain == null) return null;

        return new AsignacionResponse(
                domain.getIdAsignacion() != null ? domain.getIdAsignacion().toString() : null,
                domain.getIdActivo() != null ? domain.getIdActivo().toString() : null,
                domain.getIdUsuario() != null ? domain.getIdUsuario().toString() : null,
                domain.getFechaAsignacion() != null ? domain.getFechaAsignacion().toString() : null,
                domain.getFechaDevolucion() != null ? domain.getFechaDevolucion().toString() : null,
                domain.getEstadoAsignacion() != null ? domain.getEstadoAsignacion().name() : null,
                domain.getObservaciones()
        );
    }
}
