package com.gestionactivos.asset.application.categoria;

import com.gestionactivos.asset.domain.activo.Activo;
import com.gestionactivos.asset.domain.categoria.Categoria;
import com.gestionactivos.asset.domain.categoria.ports.in.ICategoriaUsecaseInPort;
import com.gestionactivos.asset.domain.categoria.ports.out.ICategoriaRepositoryOutPort;
import com.gestionactivos.asset.domain.categoria.utils.CategoriaFiltro;
import com.gestionactivos.asset.domain.common.ErrorCatalog;
import com.gestionactivos.asset.domain.common.OperationResult;
import com.gestionactivos.asset.domain.common.PagedResult;
import com.gestionactivos.asset.infrastructure.persistence.categoria.CategoriaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoriaUsecase implements ICategoriaUsecaseInPort {

    private final ICategoriaRepositoryOutPort categoriaRepositoryOutPort;

    @Override
    public OperationResult<Categoria> crear(Categoria categoria) {
        try {
            categoria.setIdCategoria(null);
            categoria.setEstaEliminado(false);
            Categoria guardada = categoriaRepositoryOutPort.guardar(categoria);
            return OperationResult.success(guardada);
        } catch (Exception e) {
            return OperationResult.failureSingle(
                    ErrorCatalog.CATEGORIA_SAVE_ERROR.getErrorCode(),
                    ErrorCatalog.CATEGORIA_SAVE_ERROR.getErrorMessage()
            );
        }
    }

    @Override
    public OperationResult<Categoria> actualizar(UUID id, Categoria categoria) {
        try {
            Optional<Categoria> existenteOpt = categoriaRepositoryOutPort.obtenerPorId(id);

            if (existenteOpt.isEmpty()) {
                return OperationResult.failureSingle(
                        ErrorCatalog.CATEGORIA_NOT_FOUND.getErrorCode(),
                        ErrorCatalog.CATEGORIA_NOT_FOUND.getErrorMessage()
                );
            }

            Categoria existente = existenteOpt.get();
            if (categoria.getIdCategoria() != null && !id.equals(categoria.getIdCategoria())) {
                return OperationResult.failureSingle(
                        ErrorCatalog.CATEGORIA_ID_MISMATCH.getErrorCode(),
                        ErrorCatalog.CATEGORIA_ID_MISMATCH.getErrorMessage()
                );
            }

            categoria.setIdCategoria(id);
            Categoria actualizada = categoriaRepositoryOutPort.guardar(categoria);
            return OperationResult.success(actualizada);

        } catch (Exception e) {
            return OperationResult.failureSingle(
                    ErrorCatalog.CATEGORIA_SAVE_ERROR.getErrorCode(),
                    ErrorCatalog.CATEGORIA_SAVE_ERROR.getErrorMessage()
            );
        }
    }

    @Override
    public OperationResult<Boolean> eliminarPorId(UUID idCategoria) {
        boolean eliminado = categoriaRepositoryOutPort.eliminarPorId(idCategoria);

        if (eliminado) {
            return OperationResult.success(true);
        }

        return OperationResult.failureSingle(
                ErrorCatalog.CATEGORIA_NOT_FOUND.getErrorCode(),
                "No se pudo eliminar: La categoría no existe."
        );
    }

    @Override
    public OperationResult<Categoria> obtenerPorId(UUID idCategoria) {
        return categoriaRepositoryOutPort.obtenerPorId(idCategoria)
                .map(OperationResult::success)
                .orElseGet(() -> OperationResult.failureSingle(
                        ErrorCatalog.CATEGORIA_NOT_FOUND.getErrorCode(),
                        ErrorCatalog.CATEGORIA_NOT_FOUND.getErrorMessage()
                ));
    }

    @Override
    public OperationResult<PagedResult<Categoria>> listar(CategoriaFiltro filtros, int page, int size) {
        try {
            PagedResult<Categoria> resultado = categoriaRepositoryOutPort.listar(filtros, page, size);
            return OperationResult.success(resultado);
        } catch (Exception e) {
            return OperationResult.failureSingle(
                    ErrorCatalog.GENERIC_ERROR.getErrorCode(),
                    ErrorCatalog.GENERIC_ERROR.getErrorMessage()
            );
        }
    }

    @Override
    public OperationResult<List<Categoria>> listarReporte(CategoriaFiltro filtros) {
        try {
            List<Categoria> resultado = categoriaRepositoryOutPort.findAllSinPaginacion(filtros);
            return OperationResult.success(resultado);
        } catch (Exception e) {
            return OperationResult.failureSingle(
                    ErrorCatalog.GENERIC_ERROR.getErrorCode(),
                    "Error al listar los activos para reporte."
            );
        }
    }
}