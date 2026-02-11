package com.gestionactivos.security.infrastructure.rest.mappers;

import com.gestionactivos.security.domain.usuario.Usuario;
import com.gestionactivos.security.domain.usuario.utils.Rol;
import com.gestionactivos.security.infrastructure.rest.usuario.dtos.AuthRegisterRequest;
import com.gestionactivos.security.infrastructure.rest.usuario.dtos.UsuarioProfileRequest;
import com.gestionactivos.security.infrastructure.rest.usuario.dtos.UsuarioResponse;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDtoEntityMapper {

    /** Convierte un AuthRegisterRequest a Usuario (dominio) */
    public Usuario toDomain(AuthRegisterRequest request) {
        return new Usuario(
                null, // id se genera al persistir
                request.nombre(),
                request.apellido(),
                request.email(),
                request.fotoDePerfil(),
                request.password(),
                true,
                Rol.USUARIO,
                false
        );
    }

    /** Convierte un UsuarioProfileRequest a Usuario (dominio) */
    public Usuario toDomain(UsuarioProfileRequest request) {
        return new Usuario(
                null,
                request.nombre(),
                request.apellido(),
                null,
                request.fotoDePerfil(),
                null,
                true,
                null,
                false
        );
    }

    /** Convierte un Usuario (dominio) a UsuarioResponse (DTO) */
    public UsuarioResponse toResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail(),
                usuario.getFotoDePerfil(),
                usuario.isEsActivo(),
                usuario.getRol() != null ? usuario.getRol().name() : null
        );
    }
}