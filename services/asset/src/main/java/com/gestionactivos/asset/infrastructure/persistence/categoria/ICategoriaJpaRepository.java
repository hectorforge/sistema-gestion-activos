package com.gestionactivos.asset.infrastructure.persistence.categoria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface ICategoriaJpaRepository extends JpaRepository<CategoriaEntity, UUID> {
//    @Query("""
//        SELECT c FROM CategoriaEntity c
//        WHERE c.estaEliminado = false
//        AND (:nombre IS NULL OR UPPER(c.nombreCategoria) LIKE UPPER(CONCAT('%', :nombre, '%')))
//        AND (:esActivo IS NULL OR c.esActivo = :esActivo)
//    """)
//    Page<CategoriaEntity> buscarConFiltros(
//            @Param("nombre") String nombre,
//            @Param("esActivo") Boolean esActivo,
//            Pageable pageable
//    );
    @Query(
            value = """
            SELECT * FROM tbl_categoria c
            WHERE c.esta_eliminado = false
            AND c.nombre_categoria ILIKE '%' || COALESCE(:nombre, c.nombre_categoria) || '%'
            AND c.es_activo = COALESCE(:esActivo, c.es_activo)
        """,
            countQuery = """
            SELECT COUNT(*) FROM tbl_categoria c
            WHERE c.esta_eliminado = false
            AND c.nombre_categoria ILIKE '%' || COALESCE(:nombre, c.nombre_categoria) || '%'
            AND c.es_activo = COALESCE(:esActivo, c.es_activo)
        """,
            nativeQuery = true
    )
    Page<CategoriaEntity> buscarConFiltros(
            @Param("nombre") String nombre,
            @Param("esActivo") Boolean esActivo,
            Pageable pageable
    );

}