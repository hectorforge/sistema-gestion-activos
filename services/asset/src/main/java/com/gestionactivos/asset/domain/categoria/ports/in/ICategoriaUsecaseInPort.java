package com.gestionactivos.asset.domain.categoria.ports.in;

import com.gestionactivos.asset.domain.categoria.Categoria;
import com.gestionactivos.asset.domain.categoria.utils.CategoriaFiltro;
import com.gestionactivos.asset.domain.common.OperationResult;
import com.gestionactivos.asset.domain.common.PagedResult;

import java.util.UUID;

public interface ICategoriaUsecaseInPort {
    OperationResult<Categoria> crear(Categoria categoria);
    OperationResult<Categoria> actualizar(UUID id,Categoria categoria);
    OperationResult<Boolean> eliminarPorId(UUID idCategoria);
    OperationResult<Categoria> obtenerPorId(UUID idCategoria);
    OperationResult<PagedResult<Categoria>> listar(CategoriaFiltro filtros, int page, int size);
}
