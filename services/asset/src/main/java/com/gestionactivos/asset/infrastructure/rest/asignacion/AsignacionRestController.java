package com.gestionactivos.asset.infrastructure.rest.asignacion;

import com.gestionactivos.asset.domain.asignacion.Asignacion;
import com.gestionactivos.asset.domain.asignacion.ports.in.IAsignacionUsecaseInPort;
import com.gestionactivos.asset.domain.asignacion.utils.AsignacionFiltro;
import com.gestionactivos.asset.domain.asignacion.utils.EstadoAsignacion;
import com.gestionactivos.asset.domain.common.OperationResult;
import com.gestionactivos.asset.domain.common.PagedResult;
import com.gestionactivos.asset.infrastructure.rest.asignacion.dtos.AsignacionRequest;
import com.gestionactivos.asset.infrastructure.rest.asignacion.dtos.AsignacionResponse;
import com.gestionactivos.asset.infrastructure.rest.asignacion.mappers.AsignacionDtoEntityMapper;
import com.gestionactivos.asset.infrastructure.rest.asignacion.validators.ActualizarAsignacionGrupo;
import com.gestionactivos.asset.infrastructure.rest.asignacion.validators.CrearAsignacionGrupo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/assignments")
@Tag(name = "Asignaciones", description = "API para la gestión de asignaciones de activos a usuarios.")
@RequiredArgsConstructor
public class AsignacionRestController {

    private final IAsignacionUsecaseInPort asignacionUsecaseInPort;
    private final AsignacionDtoEntityMapper mapper;

    @Operation(summary = "Crear asignación")
    @PostMapping
    public OperationResult<AsignacionResponse> crear(@Validated(CrearAsignacionGrupo.class) @RequestBody AsignacionRequest request) {
        OperationResult<Asignacion> result =
                asignacionUsecaseInPort.crear(mapper.toDomain(request));

        if (result.isSuccess()) {
            return OperationResult.success(mapper.toResponse(result.data()));
        }
        return OperationResult.failureSingle(result.errorCode(), result.errorMessage());
    }

    @Operation(summary = "Actualizar asignación")
    @PutMapping("/{id}")
    public OperationResult<AsignacionResponse> actualizar(
            @PathVariable UUID id,
            @Validated(ActualizarAsignacionGrupo.class) @RequestBody AsignacionRequest request) {

        OperationResult<Asignacion> result =
                asignacionUsecaseInPort.actualizar(id, mapper.toDomain(request));

        if (result.isSuccess()) {
            return OperationResult.success(mapper.toResponse(result.data()));
        }
        return OperationResult.failureSingle(result.errorCode(), result.errorMessage());
    }

    @Operation(summary = "Obtener asignación por ID")
    @GetMapping("/{id}")
    public OperationResult<AsignacionResponse> obtenerPorId(@PathVariable UUID id) {
        OperationResult<Asignacion> result =
                asignacionUsecaseInPort.obtenerPorId(id);

        if (result.isSuccess()) {
            return OperationResult.success(mapper.toResponse(result.data()));
        }
        return OperationResult.failureSingle(result.errorCode(), result.errorMessage());
    }

    @Operation(summary = "Eliminar asignación")
    @DeleteMapping("/{id}")
    public OperationResult<Boolean> eliminar(@PathVariable UUID id) {
        return asignacionUsecaseInPort.eliminarPorId(id);
    }

    @Operation(summary = "Cambiar estado de asignación")
    @PatchMapping("/{id}/estado")
    public OperationResult<AsignacionResponse> cambiarEstado(
            @PathVariable UUID id,
            @RequestParam EstadoAsignacion nuevoEstado) {

        OperationResult<Asignacion> result =
                asignacionUsecaseInPort.cambiarEstado(id, nuevoEstado);

        if (result.isSuccess()) {
            return OperationResult.success(mapper.toResponse(result.data()));
        }
        return OperationResult.failureSingle(result.errorCode(), result.errorMessage());
    }

    @Operation(summary = "Listar asignaciones con filtros y paginación")
    @GetMapping
    public OperationResult<PagedResult<AsignacionResponse>> listar(
            @RequestParam(required = false) UUID idActivo,
            @RequestParam(required = false) UUID idUsuario,
            @RequestParam(required = false) String estado,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        EstadoAsignacion estadoEnum = estado != null ? EstadoAsignacion.valueOf(estado) : null;
        AsignacionFiltro filtro = new AsignacionFiltro(idActivo, idUsuario, estadoEnum);

        OperationResult<PagedResult<Asignacion>> result =
                asignacionUsecaseInPort.listar(filtro, page, size);

        if (result.isSuccess()) {
            List<AsignacionResponse> content = result.data().data()
                    .stream()
                    .map(mapper::toResponse)
                    .toList();

            PagedResult<AsignacionResponse> response = new PagedResult<>(
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