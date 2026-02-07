package com.gestionactivos.asset.infrastructure.persistence.categoria;

import com.gestionactivos.asset.infrastructure.persistence.activo.ActivoEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_categoria", indexes = {
        @Index(name = "idx_categoria_nombre", columnList = "nombre_categoria"),
        @Index(name = "idx_categoria_es_activo", columnList = "es_activo")
})
public class CategoriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_category")
    private UUID idCategory;

    @Column(name = "nombre_categoria", nullable = false)
    private String nombreCategoria;

    @Column(name = "abreviatura_categoria", nullable = false)
    private String abreviaturaCategoria;

    @Column(name = "descripcion_categoria")
    private String descripcionCategoria;

    @Column(name = "url_img_categoria")
    private String urlImgCategoria;

    @Column(name = "es_activo", nullable = false)
    private Boolean esActivo;

    @Column(name = "esta_eliminado", nullable = false)
    private Boolean estaEliminado;

    @OneToMany(mappedBy = "categoria", fetch = FetchType.LAZY)
    private List<ActivoEntity> activos;
}