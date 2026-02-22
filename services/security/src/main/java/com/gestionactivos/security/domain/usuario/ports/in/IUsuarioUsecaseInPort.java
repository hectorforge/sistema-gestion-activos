package com.gestionactivos.security.domain.usuario.ports.in;

import com.gestionactivos.security.domain.common.OperationResult;
import com.gestionactivos.security.domain.usuario.Usuario;
import com.gestionactivos.security.domain.usuario.utils.JwtResult;

import java.util.List;
import java.util.UUID;

public interface IUsuarioUsecaseInPort {
    OperationResult<Usuario> registrar(Usuario usuario);
    OperationResult<Usuario> obtenerPorEmail(String email);
    OperationResult<Usuario> actualizarPerfil(UUID id,Usuario usuario);
    OperationResult<Boolean> cambiarContrasena(UUID id, String contrasenaActual, String nuevaContrasena);
    OperationResult<JwtResult> iniciarSesion(String email, String password);
    OperationResult<Boolean> eliminarCuenta(UUID id);
    OperationResult<List<Usuario>> listar();
}