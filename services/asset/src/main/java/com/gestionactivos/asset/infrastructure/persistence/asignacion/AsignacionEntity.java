package com.gestionactivos.asset.infrastructure.persistence.asignacion;

import com.gestionactivos.asset.domain.asignacion.utils.EstadoAsignacion;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_asignacion", indexes = {
        @Index(name = "idx_asignacion_activo", columnList = "id_activo"),
        @Index(name = "idx_asignacion_usuario", columnList = "id_usuario"),
        @Index(name = "idx_asignacion_estado", columnList = "estado_asignacion")
})
public class AsignacionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_asignacion")
    private UUID idAsignacion;

    @Column(name = "id_activo", nullable = false)
    private UUID idActivo;

    @Column(name = "id_usuario", nullable = false)
    private UUID idUsuario;

    @Column(name = "fecha_asignacion", nullable = false)
    private LocalDate fechaAsignacion;

    @Column(name = "fecha_devolucion")
    private LocalDate fechaDevolucion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_asignacion", nullable = false, columnDefinition = "varchar(255)")
    private EstadoAsignacion estadoAsignacion;

    @Column(name = "observaciones")
    private String observaciones;
}