package com.gestionactivos.asset.infrastructure.persistence.activo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface IActivoJpaRepository extends JpaRepository<ActivoEntity, UUID> {
    @Query("SELECT a FROM ActivoEntity a WHERE a.estaEliminado = false " +
            "AND (:nombre IS NULL OR UPPER(a.nombreActivo) LIKE UPPER(CONCAT('%', :nombre, '%'))) " +
            "AND (:codigo IS NULL OR a.codigoInventario = :codigo) " +
            "AND (:categoriaId IS NULL OR a.categoria.idCategory = :categoriaId) " +
            "AND (:esActivo IS NULL OR a.esActivo = :esActivo)")
    Page<ActivoEntity> buscarConFiltros(
            @Param("nombre") String nombre,
            @Param("codigo") String codigoInventario,
            @Param("categoriaId") UUID categoriaId,
            @Param("esActivo") Boolean esActivo,
            Pageable pageable);

    @Query(value = """
        SELECT codigo_inventario
        FROM tbl_activo
        WHERE codigo_inventario LIKE CONCAT(:prefijo, '%')
        ORDER BY codigo_inventario DESC
        LIMIT 1
        """, nativeQuery = true)
    String obtenerUltimoCodigoDelDia(String prefijo);
}
