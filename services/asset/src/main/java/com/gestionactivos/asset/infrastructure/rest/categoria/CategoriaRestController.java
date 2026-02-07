package com.gestionactivos.asset.infrastructure.rest.categoria;

import com.gestionactivos.asset.domain.categoria.Categoria;
import com.gestionactivos.asset.domain.categoria.ports.in.ICategoriaUsecaseInPort;
import com.gestionactivos.asset.domain.categoria.utils.CategoriaFiltro;
import com.gestionactivos.asset.domain.common.OperationResult;
import com.gestionactivos.asset.domain.common.PagedResult;
import com.gestionactivos.asset.infrastructure.rest.categoria.dtos.CategoriaRequest;
import com.gestionactivos.asset.infrastructure.rest.categoria.dtos.CategoriaResponse;
import com.gestionactivos.asset.infrastructure.rest.categoria.mappers.CategoriaDtoEntityMapper;
import com.gestionactivos.asset.infrastructure.rest.categoria.validators.ActualizarCategoriaGrupo;
import com.gestionactivos.asset.infrastructure.rest.categoria.validators.CrearCategoriaGrupo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categorias")
@Tag(name = "Categorias", description = "API para la correcta gestión de categorías dentro de la institución. Permite crear, actualizar, consultar, eliminar y listar categorías con filtros y paginación.")
@RequiredArgsConstructor
// Documentacion: http://localhost:8050/swagger-ui/index.html
public class CategoriaRestController {

    private final ICategoriaUsecaseInPort categoriaUsecase;
    private final CategoriaDtoEntityMapper mapper;

    @Operation(
            summary = "Crear una categoría",
            description = "Crea una nueva categoría en el sistema. Valida los datos de entrada y devuelve la categoría creada en caso de éxito. " +
                    "En caso de fallo, devuelve un código de error y un mensaje descriptivo del problema.")
    @PostMapping
    public OperationResult<CategoriaResponse> crear(
            @Validated(CrearCategoriaGrupo.class) @RequestBody CategoriaRequest request) {

        OperationResult<Categoria> result = categoriaUsecase.crear(mapper.toDomain(request));
        if (result.isSuccess()) {
            return OperationResult.success(mapper.toResponse(result.data()));
        }
        return OperationResult.failureSingle(result.errorCode(), result.errorMessage());
    }

    @Operation(
            summary = "Actualizar una categoría",
            description = "Actualiza una categoría existente. Se valida que la categoría exista y que el ID proporcionado coincida con el de la entidad. " +
                    "Devuelve la categoría actualizada en caso de éxito o un error si la operación falla.")
    @PutMapping("/{id}")
    public OperationResult<CategoriaResponse> actualizar(
            @Validated(ActualizarCategoriaGrupo.class) @RequestBody CategoriaRequest request,
            @PathVariable UUID id) {

        OperationResult<Categoria> result = categoriaUsecase.actualizar(id, mapper.toDomain(request));
        if (result.isSuccess()) {
            return OperationResult.success(mapper.toResponse(result.data()));
        }
        return OperationResult.failureSingle(result.errorCode(), result.errorMessage());
    }

    @Operation(
            summary = "Obtener categoría por ID",
            description = "Recupera una categoría utilizando su ID único. Devuelve la categoría si existe, " +
                    "o un error indicando que no se encontró.")
    @GetMapping("/{id}")
    public OperationResult<CategoriaResponse> obtenerPorId(@PathVariable UUID id) {
        OperationResult<Categoria> result = categoriaUsecase.obtenerPorId(id);
        if (result.isSuccess()) {
            return OperationResult.success(mapper.toResponse(result.data()));
        }
        return OperationResult.failureSingle(result.errorCode(), result.errorMessage());
    }

    @Operation(
            summary = "Eliminar una categoría",
            description = "Elimina una categoría por su ID. Devuelve true si se eliminó correctamente, " +
                    "o un error si la categoría no existe.")
    @DeleteMapping("/{id}")
    public OperationResult<Boolean> eliminar(@PathVariable UUID id) {
        return categoriaUsecase.eliminarPorId(id);
    }

    @Operation(
            summary = "Listar categorías con filtros y paginación",
            description = "Lista las categorías según filtros opcionales (nombre y estado activo) y permite paginación. " +
                    "Devuelve un PagedResult con las categorías encontradas, incluyendo información de paginación, " +
                    "o un error en caso de fallo.")
    @GetMapping
    public OperationResult<PagedResult<CategoriaResponse>> listar(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Boolean esActivo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        OperationResult<PagedResult<Categoria>> result =
                categoriaUsecase.listar(new CategoriaFiltro(nombre, esActivo), page, size);

        if (result.isSuccess()) {
            List<CategoriaResponse> content = result.data().data()
                    .stream()
                    .map(mapper::toResponse)
                    .toList();

            PagedResult<CategoriaResponse> response = new PagedResult<>(
                    content,
                    result.data().pageNumber(),
                    result.data().pageSize(),
                    result.data().totalElements(),
                    result.data().totalPages(),
                    result.data().first(),
                    result.data().last(),
                    result.data().hasNext(),
                    result.data().hasPrevious(),
                    result.data().isEmpty()
            );

            return OperationResult.success(response);
        }
        return OperationResult.failureSingle(result.errorCode(), result.errorMessage());
    }
}