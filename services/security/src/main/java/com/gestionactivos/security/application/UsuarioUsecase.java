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
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioUsecase implements IUsuarioUsecaseInPort {

    private final IUsuarioRepositoryOutPort repository;
    private final IJwtGeneratorInPort jwtGenerator;
    private final IPasswordEncoderInPort passwordEncoder;

    @Override
    public OperationResult<Usuario> registrar(Usuario usuario) {
        try {
            if (repository.buscarPorEmail(usuario.getEmail()).isPresent()) {
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
                return OperationResult.failureSingle(
                        ErrorCatalog.USUARIO_NOT_FOUND.getErrorCode(),
                        ErrorCatalog.USUARIO_NOT_FOUND.getErrorMessage()
                );
            }

            Usuario usuario = optionalUsuario.get();

            if (!usuario.isEsActivo() || usuario.isEstaEliminado()) {
                return OperationResult.failureSingle(
                        ErrorCatalog.USUARIO_INACTIVO.getErrorCode(),
                        ErrorCatalog.USUARIO_INACTIVO.getErrorMessage()
                );
            }

            if (!passwordEncoder.matches(password, usuario.getPass())) {
                return OperationResult.failureSingle(
                        ErrorCatalog.CONTRASENA_INCORRECTA.getErrorCode(),
                        ErrorCatalog.CONTRASENA_INCORRECTA.getErrorMessage()
                );
            }

            String token = jwtGenerator.generateToken(usuario);
            if (token == null) {
                return OperationResult.failureSingle(
                        ErrorCatalog.JWT_GENERATION_ERROR.getErrorCode(),
                        ErrorCatalog.JWT_GENERATION_ERROR.getErrorMessage()
                );
            }

            return OperationResult.success(new JwtResult(token, usuario.getEmail(), usuario.getRol().name()));

        } catch (Exception e) {
            return OperationResult.failureSingle(
                    ErrorCatalog.GENERIC_ERROR.getErrorCode(),
                    ErrorCatalog.GENERIC_ERROR.getErrorMessage()
            );
        }
    }

    @Override
    public OperationResult<Usuario> actualizarPerfil(Usuario usuario) {
        try {
            Optional<Usuario> optionalUsuario = repository.obtenerPorId(usuario.getId());
            if (optionalUsuario.isEmpty()) {
                return OperationResult.failureSingle(
                        ErrorCatalog.USUARIO_NOT_FOUND.getErrorCode(),
                        ErrorCatalog.USUARIO_NOT_FOUND.getErrorMessage()
                );
            }

            Usuario existing = optionalUsuario.get();
            existing.setNombre(usuario.getNombre());
            existing.setApellido(usuario.getApellido());
            existing.setFotoDePerfil(usuario.getFotoDePerfil());

            Usuario updated = repository.guardar(existing);
            return OperationResult.success(updated);

        } catch (Exception e) {
            return OperationResult.failureSingle(
                    ErrorCatalog.GENERIC_ERROR.getErrorCode(),
                    ErrorCatalog.GENERIC_ERROR.getErrorMessage()
            );
        }
    }

    @Override
    public OperationResult<Boolean> cambiarContrasena(UUID id, String contrasenaActual, String nuevaContrasena) {
        try {
            Optional<Usuario> optionalUsuario = repository.obtenerPorId(id);
            if (optionalUsuario.isEmpty()) {
                return OperationResult.failureSingle(
                        ErrorCatalog.USUARIO_NOT_FOUND.getErrorCode(),
                        ErrorCatalog.USUARIO_NOT_FOUND.getErrorMessage()
                );
            }

            Usuario usuario = optionalUsuario.get();
            if (!passwordEncoder.matches(contrasenaActual, usuario.getPass())) {
                return OperationResult.failureSingle(
                        ErrorCatalog.CONTRASENA_INCORRECTA.getErrorCode(),
                        ErrorCatalog.CONTRASENA_INCORRECTA.getErrorMessage()
                );
            }

            usuario.setPass(passwordEncoder.encode(nuevaContrasena));
            repository.guardar(usuario);

            return OperationResult.success(true);

        } catch (Exception e) {
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
                return OperationResult.failureSingle(
                        ErrorCatalog.USUARIO_NOT_FOUND.getErrorCode(),
                        ErrorCatalog.USUARIO_NOT_FOUND.getErrorMessage()
                );
            }
            return OperationResult.success(true);

        } catch (Exception e) {
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
                return OperationResult.failureSingle(
                        ErrorCatalog.USUARIO_NOT_FOUND.getErrorCode(),
                        ErrorCatalog.USUARIO_NOT_FOUND.getErrorMessage()
                );
            }

            return OperationResult.success(usuarioOpt.get());

        }catch (Exception e){
            return OperationResult.failureSingle(
                    ErrorCatalog.GENERIC_ERROR.getErrorCode(),
                    ErrorCatalog.GENERIC_ERROR.getErrorMessage()
            );
        }
    }
}