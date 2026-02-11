package com.gestionactivos.security.infrastructure.persistence.mappers;

import com.gestionactivos.security.domain.usuario.Usuario;
import com.gestionactivos.security.infrastructure.persistence.usuario.UsuarioEntity;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDomainEntityMapper {

    /** Convierte de dominio a entidad JPA */
    public UsuarioEntity toEntity(Usuario usuario) {
        if (usuario == null) return null;

        UsuarioEntity entity = new UsuarioEntity();
        entity.setId(usuario.getId());
        entity.setNombre(usuario.getNombre());
        entity.setApellido(usuario.getApellido());
        entity.setEmail(usuario.getEmail());
        entity.setFotoDePerfil(usuario.getFotoDePerfil());
        entity.setPass(usuario.getPass());
        entity.setEsActivo(usuario.isEsActivo());
        entity.setRol(usuario.getRol());
        return entity;
    }

    /** Convierte de entidad JPA a dominio */
    public Usuario toDomain(UsuarioEntity entity) {
        if (entity == null) return null;

        Usuario usuario = new Usuario();
        usuario.setId(entity.getId());
        usuario.setNombre(entity.getNombre());
        usuario.setApellido(entity.getApellido());
        usuario.setEmail(entity.getEmail());
        usuario.setFotoDePerfil(entity.getFotoDePerfil());
        usuario.setPass(entity.getPass());
        usuario.setEsActivo(entity.isEsActivo());
        usuario.setRol(entity.getRol());
        return usuario;
    }
}