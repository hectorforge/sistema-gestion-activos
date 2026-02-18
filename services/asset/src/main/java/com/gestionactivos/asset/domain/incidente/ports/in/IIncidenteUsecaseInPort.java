package com.gestionactivos.asset.domain.incidente.ports.in;

import com.gestionactivos.asset.domain.common.OperationResult;
import com.gestionactivos.asset.domain.common.PagedResult;
import com.gestionactivos.asset.domain.incidente.Incidente;
import com.gestionactivos.asset.domain.incidente.utils.EstadoIncidente;
import com.gestionactivos.asset.domain.incidente.utils.IncidenteFiltro;
import com.gestionactivos.asset.domain.incidente.utils.TipoIncidente;

import java.util.UUID;

public interface IIncidenteUsecaseInPort {
    OperationResult<Incidente> crear(Incidente incidente);
    OperationResult<Incidente> actualizar(UUID id, Incidente incidente);
    OperationResult<Boolean> eliminarPorId(UUID id);
    OperationResult<Incidente> obtenerPorId(UUID id);
    OperationResult<PagedResult<Incidente>> listar(IncidenteFiltro filtro, int page, int size);
    OperationResult<Incidente> cambiarEstado(UUID id, EstadoIncidente nuevoEstado);
    OperationResult<Incidente> cambiarTipo(UUID id, TipoIncidente nuevoEstado);
}