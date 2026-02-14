package com.gestionactivos.security.application;

import com.gestionactivos.security.domain.common.ErrorCatalog;
import com.gestionactivos.security.domain.common.OperationResult;
import com.gestionactivos.security.domain.usuario.Usuario;
import com.gestionactivos.security.domain.usuario.ports.in.IJwtGeneratorInPort;
import com.gestionactivos.security.domain.usuario.ports.in.IPasswordEncoderInPort;
import com.gestionactivos.security.domain.usuario.ports.in.IUsuarioUsecaseInPort;
import com.gestionactivos.security.domain.usuario.ports.out.IUsuarioRepositoryOutPort;
import com.gestionactivos.security.domain.usuario.utils.JwtResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioUsecase implements IUsuarioUsecaseInPort {

    private final IUsuarioRepositoryOutPort repository;
    private final IJwtGeneratorInPort jwtGenerator;
    private final IPasswordEncoderInPort passwordEncoder;

    @Override
    public OperationResult<Usuario> registrar(Usuario usuario) {
        try {
            if (repository.buscarPorEmail(usuario.getEmail()).isPresent()) {
                log.warn("Registro fallido: email duplicado: {}", usuario.getEmail());
                return OperationResult.failureSingle(
                        ErrorCatalog.USUARIO_EMAIL_DUPLICADO.getErrorCode(),
                        ErrorCatalog.USUARIO_EMAIL_DUPLICADO.getErrorMessage()
                );
            }

            usuario.setPass(passwordEncoder.encode(usuario.getPass()));
            usuario.setEsActivo(true);
            usuario.setEstaEliminado(false);

            Usuario saved = repository.guardar(usuario);
            return OperationResult.success(saved);
        } catch (Exception e) {
            log.error("Error al registrar un usuario con email: {}", usuario != null ? usuario.getEmail() : "<unknown>", e);
            return OperationResult.failureSingle(
                    ErrorCatalog.GENERIC_ERROR.getErrorCode(),
                    ErrorCatalog.GENERIC_ERROR.getErrorMessage()
            );
        }
    }

    @Override
    public OperationResult<JwtResult> iniciarSesion(String email, String password) {
        try {
            Optional<Usuario> optionalUsuario = repository.buscarPorEmail(email);
            if (optionalUsuario.isEmpty()) {
                log.warn("Inicio de sesión fallido: usuario no encontrado: {}", email);
                return OperationResult.failureSingle(
                        ErrorCatalog.USUARIO_NOT_FOUND.getErrorCode(),
                        ErrorCatalog.USUARIO_NOT_FOUND.getErrorMessage()
                );
            }

            Usuario usuario = optionalUsuario.get();

            if (!usuario.isEsActivo() || usuario.isEstaEliminado()) {
                log.warn("Inicio de sesión fallido: usuario inactivo o eliminado: {}", email);
                return OperationResult.failureSingle(
                        ErrorCatalog.USUARIO_INACTIVO.getErrorCode(),
                        ErrorCatalog.USUARIO_INACTIVO.getErrorMessage()
                );
            }

            if (!passwordEncoder.matches(password, usuario.getPass())) {
                log.warn("Inicio de sesión fallido: contraseña incorrecta para el usuario: {}", email);
                return OperationResult.failureSingle(
                        ErrorCatalog.CONTRASENA_INCORRECTA.getErrorCode(),
                        ErrorCatalog.CONTRASENA_INCORRECTA.getErrorMessage()
                );
            }

            String token = jwtGenerator.generateToken(usuario);
            if (token == null) {
                log.warn("Error al generar JWT para el usuario: {}", email);
                return OperationResult.failureSingle(
                        ErrorCatalog.JWT_GENERATION_ERROR.getErrorCode(),
                        ErrorCatalog.JWT_GENERATION_ERROR.getErrorMessage()
                );
            }

            return OperationResult.success(new JwtResult(token, usuario.getEmail(), usuario.getRol().name()));

        } catch (Exception e) {
            log.error("Error al iniciar sesión al usuario {}", email, e);
            return OperationResult.failureSingle(
                    ErrorCatalog.GENERIC_ERROR.getErrorCode(),
                    ErrorCatalog.GENERIC_ERROR.getErrorMessage()
            );
        }
    }

    @Override
    public OperationResult<Usuario> actualizarPerfil(UUID id, Usuario usuario) {
        try {
            Optional<Usuario> optionalUsuario = repository.obtenerPorId(id);
            if (optionalUsuario.isEmpty()) {
                log.warn("Actualización de perfil fallida: usuario no encontrado. idPayload={}, idEntity={}", id, usuario.getId());
                return OperationResult.failureSingle(
                        ErrorCatalog.USUARIO_NOT_FOUND.getErrorCode(),
                        ErrorCatalog.USUARIO_NOT_FOUND.getErrorMessage()
                );
            }

            Usuario existing = optionalUsuario.get();

            if(!existing.getId().equals(id)) {
                log.warn("Intento de actualización de perfil con ids diferentes para el usuario {}",usuario.getId());
                return OperationResult.failureSingle(
                        ErrorCatalog.USUARIO_NOT_MATCH.getErrorCode(),
                        ErrorCatalog.USUARIO_NOT_MATCH.getErrorMessage()
                );
            }

            existing.setNombre(usuario.getNombre());
            existing.setApellido(usuario.getApellido());
            existing.setFotoDePerfil(usuario.getFotoDePerfil());

            Usuario updated = repository.guardar(existing);
            return OperationResult.success(updated);

        } catch (Exception e) {
            log.error("Error al actualizar el perfil para el email {}", usuario != null ? usuario.getEmail() : "<unknown>", e);
            return OperationResult.failureSingle(
                    ErrorCatalog.GENERIC_ERROR.getErrorCode(),
                    ErrorCatalog.GENERIC_ERROR.getErrorMessage()
            );
        }
    }

    @Override
    public OperationResult<Boolean> cambiarContrasena(UUID id, String contrasenaActual, String nuevaContrasena) {
        Usuario usuario = null;
        try {
            Optional<Usuario> optionalUsuario = repository.obtenerPorId(id);
            if (optionalUsuario.isEmpty()) {
                log.warn("Cambio de contraseña fallido: usuario no encontrado: {}", id);
                return OperationResult.failureSingle(
                        ErrorCatalog.USUARIO_NOT_FOUND.getErrorCode(),
                        ErrorCatalog.USUARIO_NOT_FOUND.getErrorMessage()
                );
            }

            usuario = optionalUsuario.get();
            if (!passwordEncoder.matches(contrasenaActual, usuario.getPass())) {
                log.warn("Cambio de contraseña fallido: contraseña actual incorrecta para id: {}", id);
                return OperationResult.failureSingle(
                        ErrorCatalog.CONTRASENA_INCORRECTA.getErrorCode(),
                        ErrorCatalog.CONTRASENA_INCORRECTA.getErrorMessage()
                );
            }

            usuario.setPass(passwordEncoder.encode(nuevaContrasena));
            repository.guardar(usuario);

            return OperationResult.success(true);

        } catch (Exception e) {
            log.error("Error al cambiar contraseña para el usuario id={} email={}", id, usuario != null ? usuario.getEmail() : "<unknown>", e);
            return OperationResult.failureSingle(
                    ErrorCatalog.GENERIC_ERROR.getErrorCode(),
                    ErrorCatalog.GENERIC_ERROR.getErrorMessage()
            );
        }
    }

    @Override
    public OperationResult<Boolean> eliminarCuenta(UUID id) {
        try {
            boolean eliminado = repository.eliminar(id);
            if (!eliminado) {
                log.warn("Eliminación de cuenta fallida: usuario no encontrado: {}", id);
                return OperationResult.failureSingle(
                        ErrorCatalog.USUARIO_NOT_FOUND.getErrorCode(),
                        ErrorCatalog.USUARIO_NOT_FOUND.getErrorMessage()
                );
            }
            return OperationResult.success(true);

        } catch (Exception e) {
            log.error("Error al eliminar la cuenta para id {}", id, e);
            return OperationResult.failureSingle(
                    ErrorCatalog.GENERIC_ERROR.getErrorCode(),
                    ErrorCatalog.GENERIC_ERROR.getErrorMessage()
            );
        }
    }

    @Override
    public OperationResult<Usuario> obtenerPorEmail(String email) {
        try{
            var usuarioOpt = repository.buscarPorEmail(email);
            if(usuarioOpt.isEmpty()) {
                log.warn("Obtención de usuario por email fallida: usuario no encontrado: {}", email);
                return OperationResult.failureSingle(
                        ErrorCatalog.USUARIO_NOT_FOUND.getErrorCode(),
                        ErrorCatalog.USUARIO_NOT_FOUND.getErrorMessage()
                );
            }

            return OperationResult.success(usuarioOpt.get());

        }catch (Exception e){
            log.error("Error al obtener usuario por email {}", email, e);
            return OperationResult.failureSingle(
                    ErrorCatalog.GENERIC_ERROR.getErrorCode(),
                    ErrorCatalog.GENERIC_ERROR.getErrorMessage()
            );
        }
    }
}