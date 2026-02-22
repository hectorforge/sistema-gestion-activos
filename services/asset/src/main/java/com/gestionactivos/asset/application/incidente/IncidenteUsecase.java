package com.gestionactivos.asset.application.incidente;

import com.gestionactivos.asset.domain.common.ErrorCatalog;
import com.gestionactivos.asset.domain.common.OperationResult;
import com.gestionactivos.asset.domain.common.PagedResult;
import com.gestionactivos.asset.domain.incidente.Incidente;
import com.gestionactivos.asset.domain.incidente.ports.in.IIncidenteUsecaseInPort;
import com.gestionactivos.asset.domain.incidente.ports.out.IIncidenteRepositoryOutPort;
import com.gestionactivos.asset.domain.incidente.utils.EstadoIncidente;
import com.gestionactivos.asset.domain.incidente.utils.IncidenteFiltro;
import com.gestionactivos.asset.domain.incidente.utils.TipoIncidente;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class IncidenteUsecase implements IIncidenteUsecaseInPort {

    private final IIncidenteRepositoryOutPort repository;

    @Override
    public OperationResult<Incidente> crear(Incidente incidente) {
        try {
            incidente.setIdIncidente(null);
            Incidente guardado = repository.guardar(incidente);
            return OperationResult.success(guardado);

        } catch (Exception e) {
            log.error("Error al crear incidente. activoId={} descripcion={}",
                    incidente != null ? incidente.getIdActivo() : "<null>",
                    incidente != null ? incidente.getDescripcion() : "<null>",
                    e);

            return OperationResult.failureSingle(
                    ErrorCatalog.INCIDENTE_SAVE_ERROR.getErrorCode(),
                    ErrorCatalog.INCIDENTE_SAVE_ERROR.getErrorMessage()
            );
        }
    }

    @Override
    public OperationResult<Incidente> actualizar(UUID id, Incidente incidente) {
        try {
            Optional<Incidente> existenteOpt = repository.obtenerPorId(id);

            if (existenteOpt.isEmpty()) {
                log.warn("Actualización fallida: incidente no encontrado. id={}", id);
                return OperationResult.failureSingle(
                        ErrorCatalog.INCIDENTE_NOT_FOUND.getErrorCode(),
                        ErrorCatalog.INCIDENTE_NOT_FOUND.getErrorMessage()
                );
            }

            if (incidente.getIdIncidente() != null && !id.equals(incidente.getIdIncidente())) {
                log.warn("Actualización fallida: id mismatch. idPath={} idPayload={}", id, incidente.getIdIncidente());
                return OperationResult.failureSingle(
                        ErrorCatalog.INCIDENTE_ID_MISMATCH.getErrorCode(),
                        ErrorCatalog.INCIDENTE_ID_MISMATCH.getErrorMessage()
                );
            }

            incidente.setIdIncidente(id);
            incidente.setFechaReporte(existenteOpt.get().getFechaReporte());

            Incidente actualizado = repository.guardar(incidente);
            return OperationResult.success(actualizado);

        } catch (Exception e) {
            log.error("Error al actualizar incidente. id={}", id, e);

            return OperationResult.failureSingle(
                    ErrorCatalog.INCIDENTE_UPDATE_ERROR.getErrorCode(),
                    ErrorCatalog.INCIDENTE_UPDATE_ERROR.getErrorMessage()
            );
        }
    }

    @Override
    public OperationResult<Boolean> eliminarPorId(UUID id) {
        boolean eliminado = repository.eliminarPorId(id);

        if (eliminado) {
            return OperationResult.success(true);
        }

        log.warn("Eliminación fallida: incidente no encontrado. id={}", id);
        return OperationResult.failureSingle(
                ErrorCatalog.INCIDENTE_DELETE_ERROR.getErrorCode(),
                ErrorCatalog.INCIDENTE_DELETE_ERROR.getErrorMessage()
        );
    }

    @Override
    public OperationResult<Incidente> obtenerPorId(UUID id) {
        Optional<Incidente> opt = repository.obtenerPorId(id);

        if (opt.isEmpty()) {
            log.warn("Obtención fallida: incidente no encontrado. id={}", id);
            return OperationResult.failureSingle(
                    ErrorCatalog.INCIDENTE_NOT_FOUND.getErrorCode(),
                    ErrorCatalog.INCIDENTE_NOT_FOUND.getErrorMessage()
            );
        }

        return OperationResult.success(opt.get());
    }

    @Override
    public OperationResult<PagedResult<Incidente>> listar(IncidenteFiltro filtro, int page, int size) {
        try {
            PagedResult<Incidente> resultado =
                    repository.listar(filtro, page, size);

            return OperationResult.success(resultado);

        } catch (Exception e) {
            log.error("Error al listar incidentes. filtro={} page={} size={}",
                    filtro != null ? filtro.toString() : "<null>", page, size, e);

            return OperationResult.failureSingle(
                    ErrorCatalog.INCIDENTE_LIST_ERROR.getErrorCode(),
                    ErrorCatalog.INCIDENTE_LIST_ERROR.getErrorMessage()
            );
        }
    }

    @Override
    public OperationResult<Incidente> cambiarEstado(UUID id, EstadoIncidente nuevoEstado) {

        boolean actualizado = repository.cambiarEstado(id, nuevoEstado);

        if (!actualizado) {
            log.warn("Cambio de estado fallido: incidente no encontrado. id={} estado={}", id, nuevoEstado);
            return OperationResult.failureSingle(
                    ErrorCatalog.INCIDENTE_STATE_CHANGE_ERROR.getErrorCode(),
                    ErrorCatalog.INCIDENTE_STATE_CHANGE_ERROR.getErrorMessage()
            );
        }

        Optional<Incidente> actualizadoOpt = repository.obtenerPorId(id);

        return actualizadoOpt
                .map(OperationResult::success)
                .orElseGet(() -> OperationResult.failureSingle(
                        ErrorCatalog.GENERIC_ERROR.getErrorCode(),
                        ErrorCatalog.GENERIC_ERROR.getErrorMessage()
                ));
    }

    @Override
    public OperationResult<Incidente> cambiarTipo(UUID id, TipoIncidente nuevoTipo) {

        boolean actualizado = repository.cambiarTipo(id, nuevoTipo);

        if (!actualizado) {
            log.warn("Cambio de tipo fallido: incidente no encontrado. id={} tipo={}", id, nuevoTipo);
            return OperationResult.failureSingle(
                    ErrorCatalog.INCIDENTE_TYPE_CHANGE_ERROR.getErrorCode(),
                    ErrorCatalog.INCIDENTE_TYPE_CHANGE_ERROR.getErrorMessage()
            );
        }

        Optional<Incidente> actualizadoOpt = repository.obtenerPorId(id);

        return actualizadoOpt
                .map(OperationResult::success)
                .orElseGet(() -> OperationResult.failureSingle(
                        ErrorCatalog.GENERIC_ERROR.getErrorCode(),
                        ErrorCatalog.GENERIC_ERROR.getErrorMessage()
                ));
    }
}