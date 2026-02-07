package com.gestionactivos.asset.infrastructure.rest.activo.mappers;

import com.gestionactivos.asset.domain.activo.Activo;
import com.gestionactivos.asset.domain.activo.utils.EstadoActivo;
import com.gestionactivos.asset.domain.activo.utils.UbicacionFisica;
import com.gestionactivos.asset.infrastructure.rest.activo.dtos.ActivoRequest;
import com.gestionactivos.asset.infrastructure.rest.activo.dtos.ActivoResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class ActivoDtoEntityMapper {
    public Activo toDomain(ActivoRequest request) {
        if (request == null) return null;

        Activo activo = new Activo();
        activo.setNombreActivo(request.nombreActivo());
        activo.setDescripcion(request.descripcion());
        activo.setUrlImgActivo(request.urlImgActivo());

        if (request.categoriaId() != null)
            activo.setCategoriaId(UUID.fromString(request.categoriaId()));

        if (request.estadoActual() != null)
            activo.setEstadoActual(EstadoActivo.valueOf(request.estadoActual()));

        if (request.ubicacionFisica() != null)
            activo.setUbicacionFisica(UbicacionFisica.valueOf(request.ubicacionFisica()));

        if (request.fechaIngreso() != null)
            activo.setFechaIngreso(LocalDate.parse(request.fechaIngreso()));

        if (request.valorReferencial() != null)
            activo.setValorReferencial(new BigDecimal(request.valorReferencial()));

        if (request.vidaUtilMeses() != null && !request.vidaUtilMeses().isBlank())
            activo.setVidaUtilMeses(Integer.parseInt(request.vidaUtilMeses()));

        activo.setProveedor(request.proveedor());
        activo.setObservaciones(request.observaciones());

        return activo;
    }

    public ActivoResponse toResponse(Activo domain) {
        if (domain == null) return null;

        return new ActivoResponse(
                domain.getIdActivo() != null ? domain.getIdActivo().toString() : null,
                domain.getCodigoInventario(),
                domain.getNombreActivo(),
                domain.getDescripcion(),
                domain.getUrlImgActivo(),
                domain.getCategoriaId() != null ? domain.getCategoriaId().toString() : null,
                domain.getEstadoActual() != null ? domain.getEstadoActual().name() : null,
                domain.getUbicacionFisica() != null ? domain.getUbicacionFisica().name() : null,
                domain.getFechaIngreso() != null ? domain.getFechaIngreso().toString() : null,
                domain.getValorReferencial() != null ? domain.getValorReferencial().toString() : null,
                domain.getProveedor(),
                domain.getVidaUtilMeses() != null ? domain.getVidaUtilMeses().toString() : null,
                domain.getObservaciones(),
                domain.getEsActivo() != null ? domain.getEsActivo().toString() : null,
                domain.getFechaCreacion() != null ? domain.getFechaCreacion().toString() : null,
                domain.getFechaActualizacion() != null ? domain.getFechaActualizacion().toString() : null
        );
    }
}
