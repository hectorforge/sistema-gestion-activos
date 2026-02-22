package com.gestionactivos.security.infrastructure.persistence.usuario;

import com.gestionactivos.security.domain.usuario.Usuario;
import com.gestionactivos.security.domain.usuario.utils.Rol;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface IUsuarioJpaRepository extends JpaRepository<UsuarioEntity, UUID> {
    Optional<UsuarioEntity> findByEmail(String email);

    @Query("""
        SELECT u FROM UsuarioEntity u
        WHERE (:rol IS NULL OR u.rol = :rol)
          AND (:nombre IS NULL OR LOWER(u.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')))
          AND (:email IS NULL OR LOWER(u.email) LIKE LOWER(CONCAT('%', :email, '%')))
    """)
    Page<UsuarioEntity> listarFiltrado(
            @Param("rol") Rol rol,
            @Param("nombre") String nombre,
            @Param("email") String email,
            Pageable pageable
    );
}
