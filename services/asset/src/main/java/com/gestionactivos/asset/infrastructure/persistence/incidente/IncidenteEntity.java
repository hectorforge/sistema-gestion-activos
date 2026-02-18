package com.gestionactivos.asset.infrastructure.persistence.incidente;

import com.gestionactivos.asset.domain.incidente.utils.EstadoIncidente;
import com.gestionactivos.asset.domain.incidente.utils.TipoIncidente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tbl_incidente",indexes = {
        @Index(name = "idx_tipo_incidente", columnList = "tipo_incidente"),
        @Index(name = "idx_estado_incidente", columnList = "estado_incidente"),
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IncidenteEntity {
    @Id
    @Column(name = "id_incidente", nullable = false, updatable = false)
    private UUID idIncidente;

    @Column(name = "id_activo", nullable = false)
    private UUID idActivo;

    @Column(name = "id_usuario", nullable = false)
    private UUID idUsuario;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_incidente", nullable = false)
    private TipoIncidente tipoIncidente;

    @Column(name = "descripcion", length = 1000)
    private String descripcion;

    @Column(name = "fecha_reporte", nullable = false)
    private LocalDateTime fechaReporte;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_incidente", nullable = false)
    private EstadoIncidente estadoIncidente;
}
