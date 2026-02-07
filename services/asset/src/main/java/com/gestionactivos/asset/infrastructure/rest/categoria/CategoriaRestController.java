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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categorias")
@Tag(name = "Categorias", description = "Api para la correcta gestión de categorias para la institución.")
public class CategoriaRestController {

    private final ICategoriaUsecaseInPort categoriaUsecase;
    private final CategoriaDtoEntityMapper mapper;

    @Operation(summary = "Crear una categoría")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> crear(
            @Validated(CrearCategoriaGrupo.class)
            @RequestBody CategoriaRequest request) {

        OperationResult<Categoria> result = categoriaUsecase.crear(mapper.toDomain(request));

        if (result.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(mapper.toResponse(result.data()));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @Operation(summary = "Actualizar una categoría")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @Validated(ActualizarCategoriaGrupo.class)
            @RequestBody CategoriaRequest request,
            @PathVariable UUID id) {

        OperationResult<Categoria> result =
                categoriaUsecase.actualizar(id, mapper.toDomain(request));

        if (result.isSuccess()) {
            return ResponseEntity.ok(mapper.toResponse(result.data()));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @Operation(summary = "Obtener categoría por ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable UUID id) {
        OperationResult<Categoria> result = categoriaUsecase.obtenerPorId(id);

        if (result.isSuccess()) {
            return ResponseEntity.ok(mapper.toResponse(result.data()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @Operation(summary = "Eliminar una categoría")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable UUID id) {
        OperationResult<Boolean> result = categoriaUsecase.eliminarPorId(id);

        if (result.isSuccess()) {
            return ResponseEntity.ok(result.data());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @Operation(summary = "Listar categorías")
    @GetMapping
    public ResponseEntity<?> listar(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Boolean esActivo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        CategoriaFiltro filtro = new CategoriaFiltro(nombre, esActivo);
        OperationResult<PagedResult<Categoria>> result =
                categoriaUsecase.listar(filtro, page, size);

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

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
    }
}
