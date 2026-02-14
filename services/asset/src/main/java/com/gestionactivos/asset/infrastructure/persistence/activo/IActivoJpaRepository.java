package com.gestionactivos.asset.infrastructure.persistence.activo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface IActivoJpaRepository extends JpaRepository<ActivoEntity, UUID> {
    @Query(
            value = "SELECT * FROM tbl_activo a " +
                    "WHERE a.esta_eliminado = false " +
                    "AND (:nombre IS NULL OR a.nombre_activo ILIKE '%' || :nombre || '%') " +
                    "AND (:codigo IS NULL OR a.codigo_inventario = :codigo) " +
                    "AND (:categoriaId IS NULL OR a.categoria_id = :categoriaId) " +
                    "AND (:esActivo IS NULL OR a.es_activo = :esActivo)",
            countQuery = "SELECT COUNT(*) FROM tbl_activo a " +
                    "WHERE a.esta_eliminado = false " +
                    "AND (:nombre IS NULL OR a.nombre_activo ILIKE '%' || :nombre || '%') " +
                    "AND (:codigo IS NULL OR a.codigo_inventario = :codigo) " +
                    "AND (:categoriaId IS NULL OR a.categoria_id = :categoriaId) " +
                    "AND (:esActivo IS NULL OR a.es_activo = :esActivo)",
            nativeQuery = true
    )
    Page<ActivoEntity> buscarConFiltros(
            @Param("nombre") String nombre,
            @Param("codigo") String codigoInventario,
            @Param("categoriaId") UUID categoriaId,
            @Param("esActivo") Boolean esActivo,
            Pageable pageable
    );

    @Query(value = """
        SELECT codigo_inventario
        FROM tbl_activo
        WHERE codigo_inventario LIKE CONCAT(:prefijo, '%')
        ORDER BY codigo_inventario DESC
        LIMIT 1
        """, nativeQuery = true)
    String obtenerUltimoCodigoDelDia(String prefijo);


    @Query(
            value = "SELECT * FROM tbl_activo a " +
                    "WHERE a.esta_eliminado = false " +
                    "AND (:nombre IS NULL OR a.nombre_activo ILIKE '%' || :nombre || '%') " +
                    "AND (:codigo IS NULL OR a.codigo_inventario = :codigo) " +
                    "AND (:categoriaId IS NULL OR a.categoria_id = :categoriaId) " +
                    "AND (:esActivo IS NULL OR a.es_activo = :esActivo)",
            nativeQuery = true
    )
    List<ActivoEntity> buscarConFiltrosSinPaginacion(
            @Param("nombre") String nombre,
            @Param("codigo") String codigoInventario,
            @Param("categoriaId") UUID categoriaId,
            @Param("esActivo") Boolean esActivo
    );
}
