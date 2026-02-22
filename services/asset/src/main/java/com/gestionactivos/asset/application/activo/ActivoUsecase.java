package com.gestionactivos.asset.application.activo;

import com.gestionactivos.asset.domain.activo.Activo;
import com.gestionactivos.asset.domain.activo.ports.in.IActivoUsecaseInPort;
import com.gestionactivos.asset.domain.activo.ports.out.IActivoRepositoryOutPort;
import com.gestionactivos.asset.domain.activo.ports.out.ICodigoInventarioGeneratorOutPort;
import com.gestionactivos.asset.domain.activo.utils.ActivoFiltro;
import com.gestionactivos.asset.domain.activo.utils.EstadoActivo;
import com.gestionactivos.asset.domain.categoria.Categoria;
import com.gestionactivos.asset.domain.categoria.ports.out.ICategoriaRepositoryOutPort;
import com.gestionactivos.asset.domain.common.ErrorCatalog;
import com.gestionactivos.asset.domain.common.OperationResult;
import com.gestionactivos.asset.domain.common.PagedResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivoUsecase implements IActivoUsecaseInPort {

    private final IActivoRepositoryOutPort activoRepositoryOutPort;
    private final ICodigoInventarioGeneratorOutPort codigoGenerator;
    private final ICategoriaRepositoryOutPort categoriaRepository;


    @Override
    public OperationResult<Activo> crear(Activo activo) {
        try {
            Optional<Categoria> categoriaEncontrada = categoriaRepository.obtenerPorId(activo.getCategoriaId());
            if(categoriaEncontrada.isEmpty()){
                log.warn("Creación de activo fallida: categoría no encontrada. categoriaId={}", activo.getCategoriaId());
                return OperationResult.failureSingle(
                        ErrorCatalog.CATEGORIA_NOT_FOUND.getErrorCode(),
                        ErrorCatalog.CATEGORIA_NOT_FOUND.getErrorMessage()
                );
            }
            activo.setCodigoInventario(null); // El código se genera automáticamente, no se acepta desde la entrada
            String categoriaAbreviatura = categoriaEncontrada.get().getAbreviaturaCategoria();
            String codigo = codigoGenerator.generarCodigo(categoriaAbreviatura);
            activo.setCodigoInventario(codigo);
            Activo guardado = activoRepositoryOutPort.guardar(activo);
            return OperationResult.success(guardado);
        } catch (Exception e) {
            log.error("Error al crear activo. categoriaId={}", activo != null ? activo.getCategoriaId() : "<unknown>", e);
            return OperationResult.failureSingle(
                    ErrorCatalog.ACTIVO_SAVE_ERROR.getErrorCode(),
                    ErrorCatalog.ACTIVO_SAVE_ERROR.getErrorMessage()
            );
        }
    }

    @Override
    public OperationResult<Activo> actualizar(UUID id, Activo activo) {
        try {

            Optional<Activo> existenteOpt = activoRepositoryOutPort.obtenerPorId(id);

            if (existenteOpt.isEmpty()) {
                return OperationResult.failureSingle(
                        ErrorCatalog.ACTIVO_NOT_FOUND.getErrorCode(),
                        ErrorCatalog.ACTIVO_NOT_FOUND.getErrorMessage()
                );
            }

            Activo existente = existenteOpt.get();

            // NO tocar codigoInventario
            // NO tocar fechaCreacion

            existente.setNombreActivo(activo.getNombreActivo());
            existente.setDescripcion(activo.getDescripcion());
            existente.setUrlImgActivo(activo.getUrlImgActivo());
            existente.setCategoriaId(activo.getCategoriaId());
            existente.setEstadoActual(activo.getEstadoActual());
            existente.setUbicacionFisica(activo.getUbicacionFisica());
            existente.setFechaIngreso(activo.getFechaIngreso());
            existente.setValorReferencial(activo.getValorReferencial());
            existente.setProveedor(activo.getProveedor());
            existente.setVidaUtilMeses(activo.getVidaUtilMeses());
            existente.setObservaciones(activo.getObservaciones());
            existente.setEsActivo(activo.getEsActivo());

            existente.setFechaActualizacion(LocalDateTime.now());

            Activo actualizado = activoRepositoryOutPort.guardar(existente); // 🔥 AQUÍ

            return OperationResult.success(actualizado);

        } catch (Exception e) {
            log.error("Error al actualizar activo. id={}", id, e);
            return OperationResult.failureSingle(
                    ErrorCatalog.ACTIVO_SAVE_ERROR.getErrorCode(),
                    ErrorCatalog.ACTIVO_SAVE_ERROR.getErrorMessage()
            );
        }
    }

    @Override
    public OperationResult<Boolean> cambiarEstadoNegocio(UUID idActivo, EstadoActivo nuevoEstado) {
        boolean actualizado = activoRepositoryOutPort.cambiarEstadoNegocio(idActivo, nuevoEstado);

        if (actualizado) {
            return OperationResult.success(true);
        }

        log.warn("Cambio de estado fallido: activo no encontrado. id={} nuevoEstado={}", idActivo, nuevoEstado);
        return OperationResult.failureSingle(
                ErrorCatalog.ACTIVO_NOT_FOUND.getErrorCode(),
                "No se pudo cambiar el estado: Activo no encontrado."
        );
    }

    @Override
    public OperationResult<Boolean> activarDesactivar(UUID idActivo, Boolean esActivo) {
        boolean actualizado = activoRepositoryOutPort.activarDesactivar(idActivo, esActivo);

        if (actualizado) {
            return OperationResult.success(true);
        }

        log.warn("Activar/Desactivar fallido: activo no encontrado. id={} esActivo={}", idActivo, esActivo);
        return OperationResult.failureSingle(
                ErrorCatalog.ACTIVO_NOT_FOUND.getErrorCode(),
                "No se pudo activar/desactivar: Activo no encontrado."
        );
    }

    @Override
    public OperationResult<Boolean> eliminarPorId(UUID idActivo) {
        boolean eliminado = activoRepositoryOutPort.eliminarPorId(idActivo);

        if (eliminado) {
            return OperationResult.success(true);
        }

        log.warn("Eliminación fallida: activo no encontrado. id={}", idActivo);
        return OperationResult.failureSingle(
                ErrorCatalog.ACTIVO_NOT_FOUND.getErrorCode(),
                "No se pudo eliminar: El activo no existe."
        );
    }

    @Override
    public OperationResult<Activo> obtenerPorId(UUID idActivo) {
        Optional<Activo> opt = activoRepositoryOutPort.obtenerPorId(idActivo);
        if (opt.isEmpty()) {
            log.warn("Obtención de activo fallida: no encontrado. id={}", idActivo);
            return OperationResult.failureSingle(
                    ErrorCatalog.ACTIVO_NOT_FOUND.getErrorCode(),
                    ErrorCatalog.ACTIVO_NOT_FOUND.getErrorMessage()
            );
        }
        return OperationResult.success(opt.get());
    }

    @Override
    public OperationResult<PagedResult<Activo>> listar(ActivoFiltro filtros, int page, int size) {
        try {
            PagedResult<Activo> resultado = activoRepositoryOutPort.findAll(filtros, page, size);
            return OperationResult.success(resultado);
        } catch (Exception e) {
            log.error("Error al listar los activos. filtros={} page={} size={}", filtros != null ? filtros.toString() : "<null>", page, size, e);
            return OperationResult.failureSingle(
                    ErrorCatalog.GENERIC_ERROR.getErrorCode(),
                    "Error al listar los activos."
            );
        }
    }

    @Override
    public OperationResult<List<Activo>> listarReporte(ActivoFiltro filtros) {
        try {
            List<Activo> resultado = activoRepositoryOutPort.findAllSinPaginacion(filtros);
            return OperationResult.success(resultado);
        } catch (Exception e) {
            log.error("Error al listar todos los activos. filtros={}",
                    filtros != null ? filtros.toString() : "<null>", e);

            return OperationResult.failureSingle(
                    ErrorCatalog.GENERIC_ERROR.getErrorCode(),
                    "Error al listar los activos para reporte."
            );
        }
    }

}