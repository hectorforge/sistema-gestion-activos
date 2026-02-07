package com.gestionactivos.asset.infrastructure.config;

import com.gestionactivos.asset.infrastructure.persistence.activo.ActivoEntity;
import com.gestionactivos.asset.infrastructure.persistence.activo.IActivoJpaRepository;
import com.gestionactivos.asset.infrastructure.persistence.categoria.CategoriaEntity;
import com.gestionactivos.asset.domain.activo.utils.EstadoActivo;
import com.gestionactivos.asset.domain.activo.utils.UbicacionFisica;
import com.gestionactivos.asset.infrastructure.persistence.categoria.ICategoriaJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class ActivoCategoriaSeeder implements CommandLineRunner {

    private final ICategoriaJpaRepository categoriaRepository;
    private final IActivoJpaRepository activoRepository;

    @Override
    public void run(String... args) {

        if (categoriaRepository.count() > 0) return;

        List<CategoriaEntity> categorias = List.of(
                crearCategoria("Laptops"),
                crearCategoria("Monitores"),
                crearCategoria("Teclados"),
                crearCategoria("Mouses"),
                crearCategoria("Impresoras")
        );

        categoriaRepository.saveAll(categorias);

        categorias.forEach(cat -> {
            activoRepository.save(crearActivo("INV-" + cat.getNombreCategoria() + "-001", cat));
            activoRepository.save(crearActivo("INV-" + cat.getNombreCategoria() + "-002", cat));
        });
    }

    private CategoriaEntity crearCategoria(String nombre) {
        return CategoriaEntity.builder()
                .nombreCategoria(nombre)
                .descripcionCategoria("Categoria de " + nombre)
                .urlImgCategoria("")
                .esActivo(true)
                .estaEliminado(false)
                .build();
    }

    private ActivoEntity crearActivo(String codigo, CategoriaEntity categoria) {
        return ActivoEntity.builder()
                .codigoInventario(codigo)
                .nombreActivo("Activo " + codigo)
                .descripcion("Activo de prueba")
                .categoria(categoria)
                .estadoActual(EstadoActivo.OPERATIVO)
                .ubicacionFisica(UbicacionFisica.ALMACEN)
                .fechaIngreso(LocalDate.now())
                .valorReferencial(BigDecimal.valueOf(1500))
                .proveedor("HP")
                .vidaUtilMeses(36)
                .observaciones("Seeder inicial")
                .esActivo(true)
                .estaEliminado(false)
                .fechaCreacion(LocalDateTime.now())
                .fechaActualizacion(LocalDateTime.now())
                .build();
    }
}