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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/assets")
@Tag(name = "Activos", description = "API para la gestión de activos de la institución. Permite crear, actualizar, consultar, activar/desactivar y eliminar activos, así como listar activos con filtros y paginación.")

@RequiredArgsConstructor
// Documentacion: http://localhost:8050/swagger-ui/index.html
public class ActivoRestController {

    private final IActivoUsecaseInPort activoUsecase;
    private final ActivoDtoEntityMapper mapper;

    @Operation(
            summary = "Crear un activo",
            description = "Crea un nuevo activo en el sistema. Verifica que la categoría exista y genera automáticamente el código de inventario. Devuelve el activo creado o un error si falla la operación.")
    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public OperationResult<ActivoResponse> crear(@Validated(CrearActivoGrupo.class) @RequestBody ActivoRequest request) {
        OperationResult<Activo> result = activoUsecase.crear(mapper.toDomain(request));
        if (result.isSuccess()) {
            return OperationResult.success(mapper.toResponse(result.data()));
        }
        return OperationResult.failureSingle(result.errorCode(), result.errorMessage());
    }

    @Operation(
            summary = "Actualizar un activo",
            description = "Actualiza un activo existente. Valida que el activo exista y que el ID proporcionado coincida. Devuelve el activo actualizado o un error si falla la operación.")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public OperationResult<ActivoResponse> actualizar(@Validated(ActualizarActivoGrupo.class) @RequestBody ActivoRequest request, @PathVariable UUID id) {
        OperationResult<Activo> result = activoUsecase.actualizar(id, mapper.toDomain(request));
        if (result.isSuccess()) {
            return OperationResult.success(mapper.toResponse(result.data()));
        }
        return OperationResult.failureSingle(result.errorCode(), result.errorMessage());
    }

    @Operation(
            summary = "Obtener un activo por ID",
            description = "Recupera un activo utilizando su ID único. Devuelve el activo si existe o un error indicando que no se encontró.")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','TRABAJADOR','USUARIO')")
    public OperationResult<ActivoResponse> obtenerPorId(@PathVariable UUID id) {
        OperationResult<Activo> result = activoUsecase.obtenerPorId(id);
        if (result.isSuccess()) {
            return OperationResult.success(mapper.toResponse(result.data()));
        }
        return OperationResult.failureSingle(result.errorCode(), result.errorMessage());
    }

    @Operation(
            summary = "Cambiar estado de negocio de un activo",
            description = "Cambia el estado de negocio de un activo (por ejemplo: en uso, mantenimiento, inactivo) basado en su ID. Devuelve true si se actualizó correctamente o un error si el activo no existe.")
    @PatchMapping("/{id}/estado")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public OperationResult<Boolean> cambiarEstado(@PathVariable UUID id, @RequestParam EstadoActivo nuevoEstado) {
        return activoUsecase.cambiarEstadoNegocio(id, nuevoEstado);
    }

    @Operation(
            summary = "Cambiar visibilidad de un activo",
            description = "Activa o desactiva un activo para que sea visible o no en el sistema. Devuelve true si se realizó correctamente o un error si el activo no existe.")
    @PatchMapping("/{id}/visibilidad")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public OperationResult<Boolean> cambiarVisibilidad(@PathVariable UUID id, @RequestParam Boolean esActivo) {
        return activoUsecase.activarDesactivar(id, esActivo);
    }

    @Operation(
            summary = "Eliminar un activo",
            description = "Elimina un activo por su ID. Devuelve true si se eliminó correctamente o un error si no existe.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public OperationResult<Boolean> eliminar(@PathVariable UUID id) {
        return activoUsecase.eliminarPorId(id);
    }

    @Operation(
            summary = "Listar activos con filtros y paginación",
            description = "Lista activos según filtros opcionales (nombre, código, categoría, estado activo) y permite paginación. Devuelve un PagedResult con los activos encontrados o un error en caso de fallo.")
    @GetMapping
    //@PreAuthorize("hasAnyRole('ADMINISTRADOR','TRABAJADOR','USUARIO')")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','TRABAJADOR','USUARIO')")
    public OperationResult<PagedResult<ActivoResponse>> listar(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String codigo,
            @RequestParam(required = false) UUID categoriaId,
            @RequestParam(required = false) Boolean esActivo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size, Authentication auth) {

        String principal = auth.getPrincipal().toString();
        String rol =  auth.getAuthorities().iterator().next().toString();

        OperationResult<PagedResult<Activo>> result = activoUsecase.listar(new ActivoFiltro(nombre, codigo, categoriaId, esActivo), page, size);

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
            return OperationResult.success(response);
        }
        return OperationResult.failureSingle(result.errorCode(), result.errorMessage());
    }

    @Operation(
            summary = "Listar activos para reporte",
            description = "Devuelve todos los activos sin paginación para generación de reportes.")
    @GetMapping("/report")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR')")
    public OperationResult<List<ActivoResponse>> listarParaReporte(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String codigo,
            @RequestParam(required = false) UUID categoriaId,
            @RequestParam(required = false) Boolean esActivo) {

        OperationResult<List<Activo>> result =
                activoUsecase.listarReporte(new ActivoFiltro(nombre, codigo, categoriaId, esActivo));

        if (result.isSuccess()) {
            List<ActivoResponse> response = result.data().stream()
                    .map(mapper::toResponse)
                    .toList();
            return OperationResult.success(response);
        }

        return OperationResult.failureSingle(result.errorCode(), result.errorMessage());
    }

}