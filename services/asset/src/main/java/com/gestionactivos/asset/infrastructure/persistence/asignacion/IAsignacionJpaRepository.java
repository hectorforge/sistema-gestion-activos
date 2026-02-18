package com.gestionactivos.asset.infrastructure.persistence.asignacion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface IAsignacionJpaRepository extends JpaRepository<AsignacionEntity, UUID> {
    @Query(
            value = "SELECT * FROM tbl_asignacion a " +
                    "WHERE (:idActivo IS NULL OR a.id_activo = :idActivo) " +
                    "AND (:idUsuario IS NULL OR a.id_usuario = :idUsuario) " +
                    "AND (:estado IS NULL OR a.estado_asignacion = :estado)",
            countQuery = "SELECT COUNT(*) FROM tbl_asignacion a " +
                    "WHERE (:idActivo IS NULL OR a.id_activo = :idActivo) " +
                    "AND (:idUsuario IS NULL OR a.id_usuario = :idUsuario) " +
                    "AND (:estado IS NULL OR a.estado_asignacion = :estado)",
            nativeQuery = true
    )
    Page<AsignacionEntity> buscarConFiltros(
            @Param("idActivo") UUID idActivo,
            @Param("idUsuario") UUID idUsuario,
            @Param("estado") String estado,
            Pageable pageable
    );

    @Query(
            value = "SELECT * FROM tbl_asignacion a " +
                    "WHERE (:idActivo IS NULL OR a.id_activo = :idActivo) " +
                    "AND (:idUsuario IS NULL OR a.id_usuario = :idUsuario) " +
                    "AND (:estado IS NULL OR a.estado_asignacion = :estado)",
            nativeQuery = true
    )
    List<AsignacionEntity> buscarConFiltrosSinPaginacion(
            @Param("idActivo") UUID idActivo,
            @Param("idUsuario") UUID idUsuario,
            @Param("estado") String estado
    );
}
