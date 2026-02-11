package com.gestionactivos.security.infrastructure.persistence.usuario;

import com.gestionactivos.security.domain.common.PagedResult;
import com.gestionactivos.security.domain.usuario.Usuario;
import com.gestionactivos.security.domain.usuario.ports.out.IUsuarioRepositoryOutPort;
import com.gestionactivos.security.domain.usuario.utils.Rol;
import com.gestionactivos.security.domain.usuario.utils.UsuarioFiltros;
import com.gestionactivos.security.infrastructure.persistence.mappers.UsuarioDomainEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UsuarioRepositoryOutAdapter implements IUsuarioRepositoryOutPort {

    private final IUsuarioJpaRepository usuarioJpaRepository;
    private final UsuarioDomainEntityMapper mapper;

    @Override
    @Transactional
    public Usuario guardar(Usuario usuario) {
        UsuarioEntity entity = mapper.toEntity(usuario);

        if (entity.getId() == null) {
            entity.setEsActivo(true);
        }

        UsuarioEntity saved = usuarioJpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> obtenerPorId(UUID id) {
        return usuarioJpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public boolean eliminar(UUID id) {
        return usuarioJpaRepository.findById(id).map(u ->{
            u.setEsActivo(false);
            u.setEstaEliminado(true);
            return true;
        }).orElse(false);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioJpaRepository.findByEmail(email)
                .map(mapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResult<Usuario> listar(UsuarioFiltros filtros, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UsuarioEntity> pageResult = usuarioJpaRepository.listar(
                Rol.valueOf(filtros.rol()),
                filtros.nombre(),
                filtros.email(),
                pageable
        );

        List<Usuario> usuarios = pageResult.getContent().stream()
                .map(mapper::toDomain)
                .toList();

        return new PagedResult<>(
                usuarios,
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.getTotalPages(),
                pageResult.isFirst(),
                pageResult.isLast(),
                pageResult.hasNext(),
                pageResult.hasPrevious(),
                pageResult.isEmpty()
        );
    }
}