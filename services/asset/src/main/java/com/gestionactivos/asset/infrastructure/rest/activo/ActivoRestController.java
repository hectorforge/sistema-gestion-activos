package com.gestionactivos.asset.infrastructure.rest.activo;

import com.gestionactivos.asset.domain.activo.Activo;
import com.gestionactivos.asset.domain.activo.ports.in.IActivoUsecaseInPort;
import com.gestionactivos.asset.domain.activo.utils.ActivoFiltro;
import com.gestionactivos.asset.domain.activo.utils.EstadoActivo;
import com.gestionactivos.asset.domain.common.OperationResult;
import com.gestionactivos.asset.domain.common.PagedResult;
import com.gestionactivos.asset.infrastructure.rest.activo.dtos.ActivoRequest;
import com.gestionactivos.asset.infrastructure.rest.activo.dtos.ActivoResponse;
import com.gestionactivos.asset.infrastructure.rest.activo.mappers.ActivoDtoEntityMapper;
import com.gestionactivos.asset.infrastructure.rest.activo.validators.ActualizarActivoGrupo;
import com.gestionactivos.asset.infrastructure.rest.activo.validators.CrearActivoGrupo;
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
@RequestMapping("/api/v1/activos")
@Tag(name = "Activos", description = "Api para la correcta gestión de activos para la institución.")
// http://localhost:8050/swagger-ui/index.html
public class ActivoRestController {

    private final IActivoUsecaseInPort activoUsecase;
    private final ActivoDtoEntityMapper mapper;

    @Operation(
            summary = "Crear un activo",
            description = "Crea un nuevo activo en el sistema")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> crear(@Validated(CrearActivoGrupo.class) @RequestBody ActivoRequest request) {
        OperationResult<Activo> result = activoUsecase.crear(mapper.toDomain(request));
        if (result.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(result.data()));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @Operation(
            summary = "Actualizar un activo",
            description = "Actualiza un activo en el sistema")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> actualizar(@Validated(ActualizarActivoGrupo.class) @RequestBody ActivoRequest request, @PathVariable UUID id) {
        OperationResult<Activo> result = activoUsecase.actualizar(id,mapper.toDomain(request));
        if (result.isSuccess()) {
            return ResponseEntity.status(HttpStatus.OK).body(mapper.toResponse(result.data()));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @Operation(
            summary = "Obtener un activo",
            description = "Obtiene un activo por su ID")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> obtenerPorId(@PathVariable UUID id) {
        OperationResult<Activo> result = activoUsecase.obtenerPorId(id);
        if (result.isSuccess()) {
            return ResponseEntity.ok(mapper.toResponse(result.data()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @Operation(
            summary = "Cambiar estado de un activo",
            description = "Cambia el estado de negocio de un activo por su ID")
    @PatchMapping("/{id}/estado")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> cambiarEstado(@PathVariable UUID id, @RequestParam EstadoActivo nuevoEstado) {
        OperationResult<Boolean> result = activoUsecase.cambiarEstadoNegocio(id, nuevoEstado);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result.data());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @Operation(
            summary = "Cambiar visibilidad de un activo",
            description = "Activa o desactiva un activo por su ID")
    @PatchMapping("/{id}/visibilidad")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> cambiarVisibilidad(@PathVariable UUID id, @RequestParam Boolean esActivo) {
        OperationResult<Boolean> result = activoUsecase.activarDesactivar(id, esActivo);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result.data());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @Operation(
            summary = "Eliminar un activo",
            description = "Elimina un activo por su ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> eliminar(@PathVariable UUID id) {
        OperationResult<Boolean> result = activoUsecase.eliminarPorId(id);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result.data());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @Operation(
            summary = "Listar activos",
            description = "Lista activos con filtros y paginación")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> listar(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String codigo,
            @RequestParam(required = false) UUID categoriaId,
            @RequestParam(required = false) Boolean esActivo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        ActivoFiltro filtro = new ActivoFiltro(nombre, codigo, categoriaId, esActivo);
        OperationResult<PagedResult<Activo>> result = activoUsecase.listar(filtro, page, size);

        if (result.isSuccess()) {
            List<ActivoResponse> content = result.data().data().stream()
                    .map(mapper::toResponse)
                    .toList();

            PagedResult<ActivoResponse> response = new PagedResult<>(
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