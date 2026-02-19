package com.gestionactivos.asset.infrastructure.rest.incidente;

import com.gestionactivos.asset.domain.common.OperationResult;
import com.gestionactivos.asset.domain.common.PagedResult;
import com.gestionactivos.asset.domain.incidente.Incidente;
import com.gestionactivos.asset.domain.incidente.ports.in.IIncidenteUsecaseInPort;
import com.gestionactivos.asset.domain.incidente.utils.EstadoIncidente;
import com.gestionactivos.asset.domain.incidente.utils.IncidenteFiltro;
import com.gestionactivos.asset.domain.incidente.utils.TipoIncidente;
import com.gestionactivos.asset.infrastructure.rest.incidente.dtos.IncidenteRequest;
import com.gestionactivos.asset.infrastructure.rest.incidente.dtos.IncidenteResponse;
import com.gestionactivos.asset.infrastructure.rest.incidente.mappers.IncidenteDtoEntityMapper;
import com.gestionactivos.asset.infrastructure.rest.incidente.validators.ActualizarIncidenteGrupo;
import com.gestionactivos.asset.infrastructure.rest.incidente.validators.CrearIncidenteGrupo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/incidents")
@RequiredArgsConstructor
@Tag(name = "Incidentes", description = "API para la gestión de incidentes asociados a activos.")
public class IncidenteRestController {

    private final IIncidenteUsecaseInPort incidenteUsecaseInPort;
    private final IncidenteDtoEntityMapper mapper;

    @Operation(summary = "Crear incidente")
    @PostMapping
    public OperationResult<IncidenteResponse> crear(
            @Validated(CrearIncidenteGrupo.class)
            @RequestBody IncidenteRequest request) {

        OperationResult<Incidente> result =
                incidenteUsecaseInPort.crear(mapper.toDomain(request));

        if (result.isSuccess()) {
            return OperationResult.success(mapper.toResponse(result.data()));
        }

        return OperationResult.failureSingle(result.errorCode(), result.errorMessage());
    }

    @Operation(summary = "Actualizar incidente")
    @PutMapping("/{id}")
    public OperationResult<IncidenteResponse> actualizar(
            @Validated(ActualizarIncidenteGrupo.class)
            @RequestBody IncidenteRequest request,
            @PathVariable UUID id) {

        OperationResult<Incidente> result =
                incidenteUsecaseInPort.actualizar(id, mapper.toDomain(request));

        if (result.isSuccess()) {
            return OperationResult.success(mapper.toResponse(result.data()));
        }

        return OperationResult.failureSingle(result.errorCode(), result.errorMessage());
    }

    @Operation(summary = "Obtener incidente por ID")
    @GetMapping("/{id}")
    public OperationResult<IncidenteResponse> obtenerPorId(@PathVariable UUID id) {

        OperationResult<Incidente> result =
                incidenteUsecaseInPort.obtenerPorId(id);

        if (result.isSuccess()) {
            return OperationResult.success(mapper.toResponse(result.data()));
        }

        return OperationResult.failureSingle(result.errorCode(), result.errorMessage());
    }

    @Operation(summary = "Eliminar incidente")
    @DeleteMapping("/{id}")
    public OperationResult<Boolean> eliminar(@PathVariable UUID id) {
        return incidenteUsecaseInPort.eliminarPorId(id);
    }

    @Operation(summary = "Listar incidentes con filtros y paginación")
    @GetMapping
    public OperationResult<PagedResult<IncidenteResponse>> listar(
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) String estado,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        TipoIncidente tipoEnum = tipo != null ? TipoIncidente.valueOf(tipo) : null;
        EstadoIncidente estadoEnum = estado != null ? EstadoIncidente.valueOf(estado) : null;

        OperationResult<PagedResult<Incidente>> result =
                incidenteUsecaseInPort.listar(
                        new IncidenteFiltro(tipoEnum, estadoEnum),
                        page,
                        size
                );

        if (result.isSuccess()) {
            List<IncidenteResponse> content = result.data().data()
                    .stream()
                    .map(mapper::toResponse)
                    .toList();

            PagedResult<IncidenteResponse> response = new PagedResult<>(
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

    @PatchMapping("/{id}/estado")
    public OperationResult<IncidenteResponse> cambiarEstado(
            @PathVariable UUID id,
            @RequestParam String estado) {

        OperationResult<Incidente> result =
                incidenteUsecaseInPort.cambiarEstado(id, EstadoIncidente.valueOf(estado));

        if (result.isSuccess()) {
            return OperationResult.success(mapper.toResponse(result.data()));
        }

        return OperationResult.failureSingle(result.errorCode(), result.errorMessage());
    }

    @PatchMapping("/{id}/tipo")
    public OperationResult<IncidenteResponse> cambiarTipo(
            @PathVariable UUID id,
            @RequestParam String tipo) {

        OperationResult<Incidente> result =
                incidenteUsecaseInPort.cambiarTipo(id, TipoIncidente.valueOf(tipo));

        if (result.isSuccess()) {
            return OperationResult.success(mapper.toResponse(result.data()));
        }

        return OperationResult.failureSingle(result.errorCode(), result.errorMessage());
    }
}