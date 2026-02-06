package com.gestionactivos.asset.domain.activo.ports.in;

import com.gestionactivos.asset.domain.activo.Activo;
import com.gestionactivos.asset.domain.activo.utils.ActivoFiltro;
import com.gestionactivos.asset.domain.activo.utils.EstadoActivo;
import com.gestionactivos.asset.domain.common.OperationResult;
import com.gestionactivos.asset.domain.common.PagedResult;

import java.util.UUID;

public interface IActivoUsecaseInPort {
    OperationResult<Activo> crear(Activo activo);
    OperationResult<Activo> actualizar(UUID id,Activo activo);
    OperationResult<Boolean> cambiarEstadoNegocio(UUID idActivo, EstadoActivo nuevoEstado);
    OperationResult<Boolean> activarDesactivar(UUID idActivo, Boolean esActivo);
    OperationResult<Boolean> eliminarPorId(UUID idActivo);
    OperationResult<Activo> obtenerPorId(UUID idActivo);
    OperationResult<PagedResult<Activo>> listar(ActivoFiltro filtros,int page, int size);
}