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
@Table(name = "tbl_activo",indexes = {
        @Index(name = "idx_activo_nombre", columnList = "nombreActivo"),
        @Index(name = "idx_activo_es_activo", columnList = "esActivo"),
        @Index(name = "idx_activo_codigo", columnList = "codigoInventario"),
        @Index(name = "idx_activo_categoria", columnList = "categoria_id"),
        @Index(name = "idx_activo_estado_eliminado", columnList = "estadoActual, estaEliminado")
},
        uniqueConstraints = {
        @UniqueConstraint(name = "uc_codigo_inventario", columnNames = {"codigoInventario"})
})
public class ActivoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idActivo;
    private String codigoInventario;
    private String nombreActivo;
    private String descripcion;
    private String urlImgActivo;
    //private UUID categoriaId;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private CategoriaEntity categoria;


    private EstadoActivo estadoActual;
    private UbicacionFisica ubicacionFisica;
    private LocalDate fechaIngreso;

    private BigDecimal valorReferencial;
    private String proveedor;
    private Integer vidaUtilMeses;
    private String observaciones;

    private Boolean esActivo;
    private Boolean estaEliminado;

    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}
