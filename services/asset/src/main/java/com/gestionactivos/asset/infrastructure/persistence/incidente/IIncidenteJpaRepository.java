package com.gestionactivos.asset.infrastructure.persistence.incidente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface IIncidenteJpaRepository extends JpaRepository<IncidenteEntity, UUID> {
    @Query(
            value = """
        SELECT * FROM tbl_incidente i
        WHERE i.tipo_incidente = COALESCE(:tipo, i.tipo_incidente)
        AND i.estado_incidente = COALESCE(:estado, i.estado_incidente)
    """,
            countQuery = """
        SELECT COUNT(*) FROM tbl_incidente i
        WHERE i.tipo_incidente = COALESCE(:tipo, i.tipo_incidente)
        AND i.estado_incidente = COALESCE(:estado, i.estado_incidente)
    """,
            nativeQuery = true
    )
    Page<IncidenteEntity> buscarConFiltros(
            @Param("tipo") String tipo,
            @Param("estado") String estado,
            Pageable pageable
    );

}