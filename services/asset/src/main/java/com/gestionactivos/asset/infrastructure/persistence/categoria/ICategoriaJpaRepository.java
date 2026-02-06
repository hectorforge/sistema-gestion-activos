package com.gestionactivos.asset.infrastructure.persistence.categoria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface ICategoriaJpaRepository extends JpaRepository<CategoriaEntity, UUID> {
    @Query("""
        SELECT c FROM CategoriaEntity c
        WHERE c.estaEliminado = false
        AND (:nombre IS NULL OR UPPER(c.nombreCategoria) LIKE UPPER(CONCAT('%', :nombre, '%')))
        AND (:esActivo IS NULL OR c.esActivo = :esActivo)
    """)
    Page<CategoriaEntity> buscarConFiltros(
            @Param("nombre") String nombre,
            @Param("esActivo") Boolean esActivo,
            Pageable pageable
    );
}