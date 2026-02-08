package com.gestionactivos.asset.domain.asignacion.ports.in;

import com.gestionactivos.asset.domain.asignacion.Asignacion;
import com.gestionactivos.asset.domain.asignacion.utils.AsignacionFiltro;
import com.gestionactivos.asset.domain.asignacion.utils.EstadoAsignacion;
import com.gestionactivos.asset.domain.common.OperationResult;
import com.gestionactivos.asset.domain.common.PagedResult;

import java.util.UUID;

public interface IAsignacionUsecaseInPort {
    OperationResult<Asignacion> crear(Asignacion asignacion);
    OperationResult<Asignacion> actualizar(UUID id, Asignacion asignacion);
    OperationResult<Boolean> eliminarPorId(UUID id);
    OperationResult<Asignacion> obtenerPorId(UUID id);
    OperationResult<PagedResult<Asignacion>> listar(AsignacionFiltro filtro, int page, int size);
    OperationResult<Asignacion> cambiarEstado(UUID id, EstadoAsignacion nuevoEstado);
}