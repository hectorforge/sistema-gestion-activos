package com.gestionactivos.asset.application.activo;

import com.gestionactivos.asset.domain.activo.Activo;
import com.gestionactivos.asset.domain.activo.ports.in.IActivoUsecaseInPort;
import com.gestionactivos.asset.domain.activo.ports.out.IActivoRepositoryOutPort;
import com.gestionactivos.asset.domain.activo.ports.out.ICodigoInventarioGeneratorOutPort;
import com.gestionactivos.asset.domain.activo.utils.ActivoFiltro;
import com.gestionactivos.asset.domain.activo.utils.EstadoActivo;
import com.gestionactivos.asset.domain.categoria.Categoria;
import com.gestionactivos.asset.domain.categoria.ports.in.ICategoriaUsecaseInPort;
import com.gestionactivos.asset.domain.categoria.ports.out.ICategoriaRepositoryOutPort;
import com.gestionactivos.asset.domain.common.ErrorCatalog;
import com.gestionactivos.asset.domain.common.OperationResult;
import com.gestionactivos.asset.domain.common.PagedResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ActivoUsecase implements IActivoUsecaseInPort {

    private final IActivoRepositoryOutPort activoRepositoryOutPort;
    private final ICodigoInventarioGeneratorOutPort codigoGenerator;
    private final ICategoriaRepositoryOutPort categoriaRepository;


    @Override
    public OperationResult<Activo> crear(Activo activo) {
        try {
            Optional<Categoria> categoriaEncontrada = categoriaRepository.obtenerPorId(activo.getCategoriaId());
            if(categoriaEncontrada.isEmpty()){
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
            if (activo.getIdActivo() != null && !id.equals(activo.getIdActivo())) {
                return OperationResult.failureSingle(
                        ErrorCatalog.ACTIVO_ID_MISMATCH.getErrorCode(),
                        ErrorCatalog.ACTIVO_INVALID_DATA.getErrorMessage()
                );
            }

            activo.setIdActivo(id);
            activo.setFechaCreacion(existente.getFechaCreacion());
            activo.setFechaActualizacion(LocalDateTime.now());

            Activo actualizado = activoRepositoryOutPort.guardar(activo);

            return OperationResult.success(actualizado);

        } catch (Exception e) {
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

        return OperationResult.failureSingle(
                ErrorCatalog.ACTIVO_NOT_FOUND.getErrorCode(),
                "No se pudo eliminar: El activo no existe."
        );
    }

    @Override
    public OperationResult<Activo> obtenerPorId(UUID idActivo) {
        return activoRepositoryOutPort.obtenerPorId(idActivo)
                .map(OperationResult::success)
                .orElseGet(() -> OperationResult.failureSingle(
                        ErrorCatalog.ACTIVO_NOT_FOUND.getErrorCode(),
                        ErrorCatalog.ACTIVO_NOT_FOUND.getErrorMessage()
                ));
    }

    @Override
    public OperationResult<PagedResult<Activo>> listar(ActivoFiltro filtros, int page, int size) {
        try {
            PagedResult<Activo> resultado = activoRepositoryOutPort.findAll(filtros, page, size);
            return OperationResult.success(resultado);
        } catch (Exception e) {
            return OperationResult.failureSingle(
                    ErrorCatalog.GENERIC_ERROR.getErrorCode(),
                    "Error al listar los activos."
            );
        }
    }
}