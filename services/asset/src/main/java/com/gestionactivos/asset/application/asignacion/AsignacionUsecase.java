package com.gestionactivos.asset.application.asignacion;

import com.gestionactivos.asset.domain.asignacion.Asignacion;
import com.gestionactivos.asset.domain.asignacion.ports.in.IAsignacionUsecaseInPort;
import com.gestionactivos.asset.domain.asignacion.ports.out.IAsignacionRepositoryOutPort;
import com.gestionactivos.asset.domain.asignacion.utils.AsignacionFiltro;
import com.gestionactivos.asset.domain.asignacion.utils.EstadoAsignacion;
import com.gestionactivos.asset.domain.common.ErrorCatalog;
import com.gestionactivos.asset.domain.common.OperationResult;
import com.gestionactivos.asset.domain.common.PagedResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AsignacionUsecase implements IAsignacionUsecaseInPort {

    private final IAsignacionRepositoryOutPort asignacionRepositoryOutPort;

    @Override
    public OperationResult<Asignacion> crear(Asignacion asignacion) {
        try {
            asignacion.setIdAsignacion(null);
            Asignacion guardada = asignacionRepositoryOutPort.guardar(asignacion);
            return OperationResult.success(guardada);
        } catch (Exception e) {
            log.error("Error al crear asignación. activoId={} usuarioId={}",
                    asignacion != null ? asignacion.getIdActivo() : "<null>",
                    asignacion != null ? asignacion.getIdUsuario() : "<null>",
                    e);

            return OperationResult.failureSingle(
                    ErrorCatalog.GENERIC_ERROR.getErrorCode(),
                    "Error al crear la asignación."
            );
        }
    }

    @Override
    public OperationResult<Asignacion> actualizar(UUID id, Asignacion asignacion) {
        try {
            Optional<Asignacion> existenteOpt = asignacionRepositoryOutPort.obtenerPorId(id);

            if (existenteOpt.isEmpty()) {
                log.warn("Actualización fallida: asignación no encontrada. id={}", id);
                return OperationResult.failureSingle(
                        ErrorCatalog.ASIGNACION_NOT_FOUND.getErrorCode(),
                        ErrorCatalog.ASIGNACION_NOT_FOUND.getErrorMessage()
                );
            }

            Asignacion existente = existenteOpt.get();

            if (asignacion.getIdAsignacion() != null && !id.equals(asignacion.getIdAsignacion())) {
                log.warn("Actualización fallida: id mismatch. idPath={} idPayload={}", id, asignacion.getIdAsignacion());
                return OperationResult.failureSingle(
                        ErrorCatalog.ASIGNACION_ID_MISMATCH.getErrorCode(),
                        ErrorCatalog.ASIGNACION_ID_MISMATCH.getErrorMessage()
                );
            }

            asignacion.setIdAsignacion(id);

            if (asignacion.getFechaAsignacion() == null) {
                asignacion.setFechaAsignacion(existente.getFechaAsignacion());
            }

            Asignacion actualizado = asignacionRepositoryOutPort.guardar(asignacion);
            return OperationResult.success(actualizado);

        } catch (Exception e) {
            log.error("Error al actualizar asignación. id={}", id, e);
            return OperationResult.failureSingle(
                    ErrorCatalog.ASIGNACION_UPDATE_ERROR.getErrorCode(),
                    ErrorCatalog.ASIGNACION_UPDATE_ERROR.getErrorMessage()
            );
        }
    }

    @Override
    public OperationResult<Boolean> eliminarPorId(UUID id) {
        boolean eliminado = asignacionRepositoryOutPort.eliminarPorId(id);

        if (eliminado) {
            return OperationResult.success(true);
        }

        log.warn("Eliminación fallida: asignación no encontrada. id={}", id);
        return OperationResult.failureSingle(
                ErrorCatalog.ASIGNACION_DELETE_ERROR.getErrorCode(),
                ErrorCatalog.ASIGNACION_DELETE_ERROR.getErrorMessage()
        );
    }

    @Override
    public OperationResult<Asignacion> obtenerPorId(UUID id) {
        Optional<Asignacion> opt = asignacionRepositoryOutPort.obtenerPorId(id);

        if (opt.isEmpty()) {
            log.warn("Obtención fallida: asignación no encontrada. id={}", id);
            return OperationResult.failureSingle(
                    ErrorCatalog.ASIGNACION_NOT_FOUND.getErrorCode(),
                    ErrorCatalog.ASIGNACION_NOT_FOUND.getErrorMessage()
            );
        }

        return OperationResult.success(opt.get());
    }

    @Override
    public OperationResult<PagedResult<Asignacion>> listar(AsignacionFiltro filtro, int page, int size) {
        try {
            PagedResult<Asignacion> resultado =
                    asignacionRepositoryOutPort.listar(filtro, page, size);

            return OperationResult.success(resultado);

        } catch (Exception e) {
            log.error("Error al listar asignaciones. filtro={} page={} size={}",
                    filtro != null ? filtro.toString() : "<null>", page, size, e);

            return OperationResult.failureSingle(
                    ErrorCatalog.ASIGNACION_LIST_ERROR.getErrorCode(),
                    ErrorCatalog.ASIGNACION_LIST_ERROR.getErrorMessage()
            );
        }
    }

    @Override
    public OperationResult<Asignacion> cambiarEstado(UUID id, EstadoAsignacion nuevoEstado) {
        boolean actualizado =
                asignacionRepositoryOutPort.cambiarEstado(id, nuevoEstado.name());

        if (!actualizado) {
            log.warn("Cambio de estado fallido: asignación no encontrada. id={} estado={}", id, nuevoEstado);
            return OperationResult.failureSingle(
                    ErrorCatalog.ASIGNACION_STATE_CHANGE_ERROR.getErrorCode(),
                    ErrorCatalog.ASIGNACION_STATE_CHANGE_ERROR.getErrorMessage()
            );
        }

        Optional<Asignacion> asignacionActualizada = asignacionRepositoryOutPort.obtenerPorId(id);

        return asignacionActualizada
                .map(OperationResult::success)
                .orElseGet(() -> OperationResult.failureSingle(
                        ErrorCatalog.GENERIC_ERROR.getErrorCode(),
                        ErrorCatalog.GENERIC_ERROR.getErrorMessage()
                ));
    }
}
