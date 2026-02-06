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
        @Index(name = "idx_categoria_nombre", columnList = "nombreCategoria"),
        @Index(name = "idx_categoria_es_activo", columnList = "esActivo")
})
public class CategoriaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idCategory;
    private String nombreCategoria;
    private String descripcionCategoria;
    private String urlImgCategoria;
    private Boolean esActivo;
    private Boolean estaEliminado;


    @OneToMany(mappedBy = "categoria", fetch = FetchType.LAZY)
    private List<ActivoEntity> activos;
}
