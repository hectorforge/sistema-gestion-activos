package com.gestionactivos.security.infrastructure.rest.usuario;

import com.gestionactivos.security.domain.common.OperationResult;
import com.gestionactivos.security.domain.usuario.Usuario;
import com.gestionactivos.security.domain.usuario.ports.in.IUsuarioUsecaseInPort;
import com.gestionactivos.security.domain.usuario.utils.JwtResult;
import com.gestionactivos.security.infrastructure.rest.usuario.dtos.*;
import com.gestionactivos.security.infrastructure.rest.mappers.UsuarioDtoEntityMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticación y Usuarios", description = "API para registro, login, gestión de perfil, cambio de contraseña y eliminación de cuenta.")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class AuthRestController {

    private final IUsuarioUsecaseInPort usuarioUsecase;
    private final UsuarioDtoEntityMapper mapper;

    @Operation(summary = "Registrar nuevo usuario", description = "Registra un usuario en el sistema. Devuelve el usuario creado o un error si el email ya existe.")
    @PostMapping("/register")
    public OperationResult<UsuarioResponse> registrar(@Validated @RequestBody AuthRegisterRequest request) {
        Usuario usuario = mapper.toDomain(request);
        OperationResult<Usuario> result = usuarioUsecase.registrar(usuario);

        if (result.isSuccess()) {
            return OperationResult.success(mapper.toResponse(result.data()));
        }

        return OperationResult.failureSingle(result.errorCode(), result.errorMessage());
    }

    @Operation(summary = "Listar todos los usuarios", description = "Listar todos los usuarios existentes.")
    @GetMapping("/listar")
    public OperationResult<List<UsuarioResponse>> listar() {
        OperationResult<List<Usuario>> result = usuarioUsecase.listar();

        if (result.isSuccess()) {
            return OperationResult.success(result.data().stream().map(mapper::toResponse).toList());
        }

        return OperationResult.failureSingle(result.errorCode(), result.errorMessage());
    }

    @Operation(summary = "Iniciar sesión", description = "Autentica un usuario y devuelve un JWT con la información del usuario.")
    @PostMapping("/login")
    public OperationResult<JwtResult> login(@Validated @RequestBody AuthLoginRequest request) {
        OperationResult<JwtResult> result = usuarioUsecase.iniciarSesion(request.email(), request.password());

        if (result.isSuccess()) {
            return OperationResult.success(result.data());
        }

        return OperationResult.failureSingle(result.errorCode(), result.errorMessage());
    }

    @Operation(summary = "Actualizar perfil de usuario", description = "Actualiza el nombre, apellido y foto de perfil del usuario.")
    @PutMapping("/profile/{id}")
    @PreAuthorize("isAuthenticated()")
    public OperationResult<UsuarioResponse> actualizarPerfil(@PathVariable UUID id, @Validated @RequestBody UsuarioProfileRequest request) {
        Usuario usuario = mapper.toDomain(request);
        //usuario.setId(id);
        OperationResult<Usuario> result = usuarioUsecase.actualizarPerfil(id,usuario);

        if (result.isSuccess()) {
            return OperationResult.success(mapper.toResponse(result.data()));
        }

        return OperationResult.failureSingle(result.errorCode(), result.errorMessage());
    }

    @Operation(summary = "Cambiar contraseña", description = "Permite al usuario cambiar su contraseña actual por una nueva.")
    @PatchMapping("/{id}/password")
    @PreAuthorize("isAuthenticated()")
    public OperationResult<Boolean> cambiarContrasena(@PathVariable UUID id, @RequestBody CambiarContrasenaRequest cambiarContrasenaRequest) {
        OperationResult<Boolean> result = usuarioUsecase.cambiarContrasena(id, cambiarContrasenaRequest.contrasenaActual(), cambiarContrasenaRequest.nuevaContrasena());

        if (result.isSuccess()) {
            return OperationResult.success(true);
        }

        return OperationResult.failureSingle(result.errorCode(), result.errorMessage());
    }

    @Operation(summary = "Eliminar cuenta", description = "Elimina la cuenta de un usuario por su ID. Devuelve true si se eliminó correctamente.")
    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public OperationResult<Boolean> eliminarCuenta(@PathVariable UUID id) {
        OperationResult<Boolean> result = usuarioUsecase.eliminarCuenta(id);

        if (result.isSuccess()) {
            return OperationResult.success(true);
        }

        return OperationResult.failureSingle(result.errorCode(), result.errorMessage());
    }

    @GetMapping("/dummy-users")
    @PreAuthorize("isAuthenticated() and hasRole('USUARIO')")
    public OperationResult<String> dummyUsers(Authentication authentication) {
        String username = authentication.getName();

        var usuarioResult = usuarioUsecase.obtenerPorEmail(username);
        String rol = usuarioResult.data().getRol().name();

        return OperationResult.success(
                String.format("Hola %s, tu rol es %s", username, rol)
        );
    }

    @GetMapping("/dummy-workers")
    @PreAuthorize("isAuthenticated() and hasRole('TRABAJADOR')")
    public OperationResult<String> dummyWorkers(Authentication authentication) {
        String username = authentication.getName();
        return OperationResult.success("Hola WORKER " + username);
    }

    @GetMapping("/dummy-admins")
    @PreAuthorize("isAuthenticated() and hasRole('ADMINISTRADOR')")
    public OperationResult<String> dummyAdmins(Authentication authentication) {
        String username = authentication.getName();
        return OperationResult.success("Hola ADMIN " + username);
    }
}