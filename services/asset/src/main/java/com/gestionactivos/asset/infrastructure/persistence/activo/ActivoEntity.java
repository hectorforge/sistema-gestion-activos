package com.gestionactivos.asset.infrastructure.persistence.activo;

import com.gestionactivos.asset.domain.activo.utils.EstadoActivo;
import com.gestionactivos.asset.domain.activo.utils.UbicacionFisica;
import com.gestionactivos.asset.infrastructure.persistence.categoria.CategoriaEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_activo", indexes = {
        @Index(name = "idx_activo_nombre", columnList = "nombre_activo"),
        @Index(name = "idx_activo_es_activo", columnList = "es_activo"),
        @Index(name = "idx_activo_codigo", columnList = "codigo_inventario"),
        @Index(name = "idx_activo_categoria", columnList = "categoria_id"),
        @Index(name = "idx_activo_estado_eliminado", columnList = "estado_actual, esta_eliminado")
}, uniqueConstraints = {
        @UniqueConstraint(name = "uc_codigo_inventario", columnNames = {"codigo_inventario"})
})
public class ActivoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_activo")
    private UUID idActivo;

    @Column(name = "codigo_inventario", nullable = false)
    private String codigoInventario;

    @Column(name = "nombre_activo", nullable = false)
    private String nombreActivo;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "url_img_activo")
    private String urlImgActivo;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private CategoriaEntity categoria;

    @Enumerated(EnumType.STRING)
    //@Column(name = "estado_actual", nullable = false)
    @Column(name = "estado_actual", nullable = false, columnDefinition = "varchar(255)")
    private EstadoActivo estadoActual;

    @Enumerated(EnumType.STRING)
    //@Column(name = "ubicacion_fisica", nullable = false)
    @Column(name = "ubicacion_fisica", nullable = false, columnDefinition = "varchar(255)")
    private UbicacionFisica ubicacionFisica;

    @Column(name = "fecha_ingreso")
    private LocalDate fechaIngreso;

    @Column(name = "valor_referencial", precision = 38, scale = 2)
    private BigDecimal valorReferencial;

    @Column(name = "proveedor")
    private String proveedor;

    @Column(name = "vida_util_meses")
    private Integer vidaUtilMeses;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "es_activo", nullable = false)
    private Boolean esActivo;

    @Column(name = "esta_eliminado", nullable = false)
    private Boolean estaEliminado;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
}
